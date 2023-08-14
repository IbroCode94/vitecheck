package com.vitmedics.vitcheck.model;

import com.vitmedics.vitcheck.model.entities.survey.*;
import com.vitmedics.vitcheck.model.enumeration.productType.ProductType;
import com.vitmedics.vitcheck.model.enumeration.resultsViewType.ResultsViewType;
import com.vitmedics.vitcheck.model.enumeration.surveyInstallationSettingType.SurveyInstallationSettingType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;

@Getter
@Accessors(chain = true)
public class SurveySubmissionResultsModel {

    private SurveySubmission submission;
    private Set<ProductType> prescribedProductTypes;
    private Set<PrescribedProduct> prescribedProducts;

    private NutrientInteractionScoreMap allNutrientInteractionScores;
    private NutrientInteractionScoreMap calculatedNutrientInteractionScores;

    @Setter
    private boolean hasNotableInteractions;

    public SurveySubmissionResultsModel(SurveySubmission submission) {
        this.submission = submission;
        this.prescribedProductTypes = new HashSet<>();
        this.prescribedProducts = new HashSet<>();

        Optional<SurveyInstallationSetting> configuredResultsViewType = submission.getSurveyInstallation().getSurveyInstallationSetting(SurveyInstallationSettingType.RESULTS_VIEW_TYPE);

        boolean heatScaleResultsViewConfigured = configuredResultsViewType.isPresent() && configuredResultsViewType.get().getCastSettingValue() == ResultsViewType.HEAT_SCALE;

        // Track (and store) additional scores that are calculated in the decision table
        this.calculatedNutrientInteractionScores = new NutrientInteractionScoreMap(VitcheckConsts.DEFAULT_SCORE_CEILING);

        // All scores combined
        this.allNutrientInteractionScores = heatScaleResultsViewConfigured
                ? new NutrientInteractionScoreMap()
                : new NutrientInteractionScoreMap(VitcheckConsts.DEFAULT_SCORE_CEILING);

        setNutrientInteractionScoreTotals();

        if(submission.getResult() != null) {
            hasNotableInteractions = submission.getResult().isHasNotableInteractions();
            prescribedProductTypes.addAll(submission.getResult().getPrescribedProductTypes().stream().map(p -> p.getProductType()).collect(Collectors.toList()));
        }
    }

    public void filterAndAddPrescribedProducts(Collection<PrescribedProduct> allAvailableProducts) {

        Set<PrescribedProduct> prescribedProducts = allAvailableProducts
                .stream()
                .filter(p -> prescribedProductTypes.contains(p.getProductType()))
                .collect(Collectors.toSet());

        this.prescribedProducts.addAll(prescribedProducts);
    }

    private void setNutrientInteractionScoreTotals() {
        List<NutrientInteraction> interactions = new ArrayList<>();

        /**
         * Settings for how to process medication scores.
         * if AGGREGATE_MEDICATION_NUTRIENT_SCORES present then calc an aggregated total score for each nutrient
         * if NUTRIENT_ID_MEDICATION_FILTER present then filter out medication interactions that aren't relevant to the nutrients in the filter
         *
        */

        Optional<SurveyInstallationSetting> aggregateMedicationNutrientScores = submission.getSurveyInstallation().getSurveyInstallationSetting(SurveyInstallationSettingType.AGGREGATE_MEDICATION_NUTRIENT_SCORES);
        Optional<SurveyInstallationSetting> medicationNutrientIdFilter = submission.getSurveyInstallation().getSurveyInstallationSetting(SurveyInstallationSettingType.NUTRIENT_ID_MEDICATION_FILTER);

        List<MedicationInteraction> medicationInteractions = getMedicationInteractions();

        if(aggregateMedicationNutrientScores.isPresent()) {

              // Group by nutrient id
              // Then flatten entryset to a set of interactions with sum'd scores
              medicationInteractions =
                  medicationInteractions.stream()
                      .collect(
                          groupingBy(
                              MedicationInteraction::getNutrientId,
                              collectingAndThen(
                                  reducing(
                                      (a, b) ->
                                          new MedicationInteraction(
                                              a.getMedication(),
                                              a.getNutrient(),
                                              Math.min(a.getScore() + b.getScore(), VitcheckConsts.DEFAULT_SCORE_CEILING))), // when aggregating, highest score permitted is 3 per nutrient
                                  Optional::get)))
                      .entrySet()
                      .stream()
                      .map(interaction -> interaction.getValue())
                      .collect(toList());
        }

        if(medicationNutrientIdFilter.isPresent()) {
            Set<Integer> nutrientIds = Arrays.stream(medicationNutrientIdFilter.get().getValue().split(","))
                    .map(Integer::parseInt).collect(toSet());

            medicationInteractions = medicationInteractions.stream().filter(interaction -> nutrientIds.contains(interaction.getNutrient().getId())).collect(toList());
        }

        interactions.addAll(medicationInteractions);
        interactions.addAll(getSurveyAnswerInteractions());
        interactions.addAll(submission.getCalculatedNutrientInteractions());

        for (NutrientInteraction interaction : interactions) {
            allNutrientInteractionScores.put(interaction.getNutrient(), interaction.getScore(), interaction.getEvidence());
        }
    }

    public List<CalculatedNutrientInteraction> getCalculatedInteractions() {
        return StreamSupport.stream(calculatedNutrientInteractionScores.spliterator(), false)
                .map(keyVal -> {
                    CalculatedNutrientInteraction interaction = new CalculatedNutrientInteraction();
                    interaction.setSurveySubmission(submission);
                    interaction.setNutrient(keyVal.getValue().getNutrient());
                    interaction.setScore(keyVal.getValue().getScore());
                    interaction.setEvidence(keyVal.getValue().getEvidence());
                    return interaction;
                }).collect(Collectors.toList());
    }

    public List<MedicationInteraction> getMedicationInteractions() {
        return submission.getMedications()
                .stream()
                .flatMap(m -> m.getMedication().getMedicationInteractions().stream())
                .collect(Collectors.toList());
    }

    public List<SurveyAnswer> getSurveyAnswers() {
        return submission.getResponses()
                .stream()
                .flatMap(m -> m.getAnswers().stream())
                .collect(Collectors.toList());
    }

    public List<SurveyAnswer> getSurveyAnswersWithTailoredResponse() {
        return getSurveyAnswers()
                .stream()
                .filter(a -> Strings.isNotEmpty(a.getAdditionalInfo()))
                .collect(Collectors.toList());
    }

    public List<SurveySubmissionMedication> getMedications() {
        return submission.getMedications();
    }

    public List<SurveyAnswerInteraction> getSurveyAnswerInteractions() {

        return submission.getResponses()
                .stream()
                .flatMap(m -> m.getAnswers().stream()
                        .flatMap(a -> a.getInteractions().stream()))
                .collect(Collectors.toList());
    }

    public boolean prescribeProductType(ProductType productType) {
        return this.prescribedProductTypes.add(productType);
    }

    public boolean prescribeProductTypes(Collection<ProductType> productTypes) {
        return this.prescribedProductTypes.addAll(productTypes);
    }

    /**
     * Track a nutrient score calculated in decision table (will only create nutrient stub), and then add this to the
     * total "allNutrientScores"
     * @param nutrientId
     * @param scoreIncrease
     */
    public void addOrUpdateAdditionalCalculatedNutrientScore(int nutrientId, float scoreIncrease) {
        NutrientScore nutrientStubScore = this.calculatedNutrientInteractionScores.put(nutrientId, scoreIncrease);
        this.allNutrientInteractionScores.put(nutrientStubScore.getNutrient(), nutrientStubScore.getScore(), null);
    }

    public boolean submissionContainsAnswerId(int answerId) {
        return !this.submission.getResponses()
                .stream()
                .filter(response -> response.getAnswers().stream().anyMatch(answer -> answer.getId() == answerId))
                .collect(Collectors.toList())
                .isEmpty();
    }

    public boolean submissionContainsAnswerIds(int[] answerIds) {
        return !this.submission.getResponses()
                .stream()
                .filter(response -> response.getAnswers().stream().anyMatch(answer -> Arrays.stream(answerIds).anyMatch(id -> id == answer.getId())))
                .collect(Collectors.toList())
                .isEmpty();
    }

    /**
    *
    * @param scoreThreshold score threshold >= to rule
    * @param nutrientIdFilters
    * @return
    */
    public boolean isNutrientScoreThresholdReached(float scoreThreshold, int[] nutrientIdFilters) {
        return StreamSupport.stream(this.allNutrientInteractionScores.spliterator(), false)
                .filter(nutrientScore -> Arrays.stream(nutrientIdFilters)
                        .anyMatch(id -> id == nutrientScore.getKey() && nutrientScore.getValue().getScore() >= scoreThreshold)
                )
                .collect(Collectors.toList()).size() > 0;

    }

    /**
     *
     * @param lowerScoreLimit lower score (inclusive)
     * @param upperScoreLimit upper score (exclusive)
     * @param nutrientIdFilter the id of the nutrient we're checking the score on
     * @return
     */
    public boolean isNutrientScoreBetween(float lowerScoreLimit, float upperScoreLimit, int nutrientIdFilter) {
        return StreamSupport.stream(this.allNutrientInteractionScores.spliterator(), false)
                .filter(nutrientScore -> nutrientScore.getKey() == nutrientIdFilter && nutrientScore.getValue().getScore() >= lowerScoreLimit && nutrientScore.getValue().getScore() < upperScoreLimit)
                .collect(Collectors.toList()).size() > 0;
    }

    public boolean isNutrientScoreThresholdCountReached(float scoreThreshold, int countThreshold) {
        return StreamSupport.stream(this.allNutrientInteractionScores.spliterator(), false)
                .filter(nutrientScore -> nutrientScore.getValue().getScore() >= scoreThreshold)
                .collect(Collectors.toList()).size() >= countThreshold;

    }

    public double calcBMI(int heightQuestionId, int weightQuestionId) {

        try {
            SurveySubmissionResponse heightResponse = submission.getResponses().stream()
                    .filter(r -> r.getQuestion().getId() == heightQuestionId).findFirst().orElseThrow();
            SurveySubmissionResponse weightResponse = submission.getResponses().stream()
                    .filter(r -> r.getQuestion().getId() == weightQuestionId).findFirst().orElseThrow();

            boolean isMetricHeight = Strings.isBlank(heightResponse.getResponseText2());
            boolean isMetricWeight = Strings.isBlank(weightResponse.getResponseText2());

            double metres;
            if(!isMetricHeight) {
                int feet = Integer.parseInt(heightResponse.getResponseText1());
                int inches = Integer.parseInt(heightResponse.getResponseText2());
                metres = convertImperialHeight(feet, inches);
            }
            else {
                metres = Float.parseFloat(heightResponse.getResponseText1());
            }

            double kgs;
            if(!isMetricWeight) {
                int pounds = Integer.parseInt(weightResponse.getResponseText1());
                int ounces = Integer.parseInt(weightResponse.getResponseText2());
                kgs = convertImperialWeight(pounds, ounces);
            }
            else {
                kgs = Float.parseFloat(weightResponse.getResponseText1());
            }

            return kgs / Math.pow(metres, 2);
        }
        catch (Exception e) {
            return 0;
        }
    }

    /**
     * See https://www.rapidtables.com/convert/length/feet-inch-to-meter.html     *
     * @param feet
     * @param inches
     * @return metres
     */
    private double convertImperialHeight(int feet, int inches) {
        return (feet * 0.3048) + (inches * 0.0254);
    }

    /**
     * See
     * https://www.rapidtables.com/convert/weight/pound-to-kg.html
     * https://www.rapidtables.com/convert/weight/ounce-to-kg.html
     * @param pounds
     * @param ounces
     * @return KGs
     */
    private double convertImperialWeight(int pounds, int ounces) {
        return (pounds * 0.45359237) + (ounces * 0.02834952);
    }
}
