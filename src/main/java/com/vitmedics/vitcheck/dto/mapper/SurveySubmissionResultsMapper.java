package com.vitmedics.vitcheck.dto.mapper;

import com.vitmedics.vitcheck.dto.model.survey.*;
import com.vitmedics.vitcheck.model.SurveySubmissionResultsModel;
import com.vitmedics.vitcheck.model.UserDetailsModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class SurveySubmissionResultsMapper {

    public static SurveySubmissionResultsDto toSurveySubmissionResultsDto(ModelMapper modelMapper, SurveySubmissionResultsModel results, Optional<UserDetailsModel> user) {

    List<SurveySubmissionResultMedicationDto> medications =
        results.getMedications().stream()
            .map(
                medication ->
                    new SurveySubmissionResultMedicationDto()
                        .setId(medication.getId())
                        .setName(medication.getMedication().getName())
                        .setDurationTaken(medication.getDurationTaken())
                        .setInteractions(
                                medication.getMedication().getMedicationInteractions()
                                    .stream().map(interaction ->
                                        new MedicationInteractionDto()
                                            .setScore(interaction.getScore())
                                            .setNutrient(NutrientMapper.toNutrientDto(interaction.getNutrient()))
                                            .setEvidence(interaction.getEvidence())
                                ).collect(Collectors.toList()))
                        )
            .collect(Collectors.toList());

        List<NutrientInteractionDto> surveyNutrientInteractions =
                StreamSupport.stream( results.getAllNutrientInteractionScores().spliterator(), false)
                        .map(interaction ->
                            new NutrientInteractionDto()
                                    .setNutrient(NutrientMapper.toNutrientDto(interaction.getValue().getNutrient()))
                                    .setScore(interaction.getValue().getScore())
                                    .setEvidence(interaction.getValue().getEvidence())
                        )
                        .filter(interaction -> interaction.getScore() > 0)
                        .collect(Collectors.toList());

        SurveySubmissionResultsDto resultsDto = new SurveySubmissionResultsDto()
                .setSurveySubmissionId(results.getSubmission().getId())
                .setHasNotableInteractions(results.isHasNotableInteractions())
                .setMedications(medications)
                .setAggregatedNutrientInteractions(surveyNutrientInteractions)
                .setTailoredResponses(results.getSurveyAnswersWithTailoredResponse()
                        .stream()
                        .map(a ->
                                new TailoredResponseDto()
                                    .setInformation(a.getAdditionalInfo())
                                    .setEvidence(a.getAdditionalInfoEvidence())
                        ).collect(Collectors.toList())
                )
                .setPrescribedProducts(modelMapper.map(results.getPrescribedProducts(), new TypeToken<Set<PrescribedProductDto>>(){}.getType()));


        if(user.isPresent())
            resultsDto.setUser(modelMapper.map(user.get(), SurveySubmissionUserDto.class));

        return resultsDto;
    }
}
