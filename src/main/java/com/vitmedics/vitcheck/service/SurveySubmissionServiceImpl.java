package com.vitmedics.vitcheck.service;

import com.vitmedics.vitcheck.dto.mapper.SurveySubmissionResultsMapper;
import com.vitmedics.vitcheck.dto.model.survey.PrescribedProductDto;
import com.vitmedics.vitcheck.dto.model.survey.SurveySubmissionDto;
import com.vitmedics.vitcheck.dto.model.survey.SurveySubmissionResultsDto;
import com.vitmedics.vitcheck.dto.model.survey.SurveySubmissionUserDto;
import com.vitmedics.vitcheck.integration.crm.CRMFactory;
import com.vitmedics.vitcheck.integration.crm.CRMService;
import com.vitmedics.vitcheck.integration.store.StoreApiClient;
import com.vitmedics.vitcheck.integration.store.StoreFactory;
import com.vitmedics.vitcheck.model.*;
import com.vitmedics.vitcheck.model.entities.survey.*;
import com.vitmedics.vitcheck.model.enumeration.crmType.CRMType;
import com.vitmedics.vitcheck.model.enumeration.productType.ProductType;
import com.vitmedics.vitcheck.model.enumeration.surveyInstallationSettingType.SurveyInstallationSettingType;
import com.vitmedics.vitcheck.repository.survey.SurveyInstallationRepository;
import com.vitmedics.vitcheck.repository.survey.SurveySubmissionRepository;
import com.vitmedics.vitcheck.repository.survey.SurveySubmissionUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kie.api.runtime.StatelessKieSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.vitmedics.vitcheck.exception.EntityType.SURVEY_INSTALLATION;
import static com.vitmedics.vitcheck.exception.EntityType.SURVEY_SUBMISSION;
import static com.vitmedics.vitcheck.exception.ExceptionType.EMAIL_SEND_ERROR;
import static com.vitmedics.vitcheck.exception.ExceptionType.ENTITY_NOT_FOUND;

@Component
public class SurveySubmissionServiceImpl extends BaseService implements SurveySubmissionService {

    private static final Logger log = LogManager.getLogger(SurveySubmissionServiceImpl.class);

    // use static rules file for now
    private static final String VITCHECK_CORE_RULES_FILENAME = "decision-tables/vitcheck-core-decision-table.xls";
    private static final int VITCHECK_SURVEY_ID = 1;
    private static final String VITDETECTOR_RULES_FILENAME = "decision-tables/vitdetector-decision-table.xls";

    @Autowired
    private SurveyInstallationRepository surveyInstallationRepository;

    @Autowired
    private SurveySubmissionRepository submissionRepository;

    @Autowired
    private SurveySubmissionUserRepository submissionUserRepository;

    @Autowired
    private SurveyDecisionTableService surveyDecisionTableService;

    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private StoreFactory storeFactory;

    @Autowired
    private CRMFactory crmFactory;

    @Value("${vitcheck.email.enabled}")
    private boolean emailEnabled;

    @Value("${vitcheck.integration.crm.enabled}")
    private boolean crmEnabled;

    @Value("${vitcheck.email.security.aes-encryption-key}")
    private String smtpEncryptionKey;

    @Value("${vitcheck.email.security.aes-encryption-iv}")
    private String smtpEncryptionIV;

    @Override
    @Transactional
    public UUID saveSubmission(UUID surveyInstallationId, SurveySubmissionDto submissionDto) {

        SurveyInstallation installation;

        try {
            installation = entityManager.getReference(SurveyInstallation.class, surveyInstallationId);
        } catch(EntityNotFoundException ex) {
            throw exception(SURVEY_INSTALLATION, ENTITY_NOT_FOUND, submissionDto.getId().toString());
        }

        final SurveySubmission submissionEntity = new SurveySubmission();
        submissionEntity.setSurveyInstallation(installation);

        // Map the survey responses to the submission
        List<SurveySubmissionResponse> responseEntities = StreamSupport.stream(submissionDto.getResponses().spliterator(), false)
                .map(responseDto -> {
                    SurveySubmissionResponse responseEntity = new SurveySubmissionResponse();
                    responseEntity.setSurveySubmission(submissionEntity);
                    responseEntity.setQuestion(entityManager.getReference(SurveyQuestion.class, responseDto.getQuestionId()));
                    responseEntity.setResponseText1(responseDto.getResponseText1());
                    responseEntity.setResponseText2(responseDto.getResponseText2());

                    List<SurveyAnswer> answerEntities = StreamSupport.stream(responseDto.getAnswerIds().spliterator(), false)
                            .map(answerId -> entityManager.getReference(SurveyAnswer.class, answerId)).collect(Collectors.toList());

                    responseEntity.setAnswers(answerEntities);
                    return responseEntity;
                }).collect(Collectors.toList());

        submissionEntity.setResponses(responseEntities);

        // Map the medications to the submission
        List<SurveySubmissionMedication> medicationEntities = StreamSupport.stream(submissionDto.getMedications().spliterator(), false)
                .map(medicationDto -> {
                    SurveySubmissionMedication medicationEntity = new SurveySubmissionMedication();
                    medicationEntity.setDurationTaken(medicationDto.getDurationTaken());
                    medicationEntity.setSurveySubmission(submissionEntity);
                    medicationEntity.setMedication(entityManager.getReference(Medication.class, medicationDto.getMedicationId()));
                    return medicationEntity;
                }).collect(Collectors.toList());

        submissionEntity.setMedications(medicationEntities);
        entityManager.persist(submissionEntity);
        entityManager.flush();
        return submissionEntity.getId();
    }

    @Override
    @Transactional
    public SurveySubmissionResultsDto processSubmission(UUID surveySubmissionId, UUID surveyInstallationId) throws IOException {

        SurveySubmission submission = submissionRepository.findById(surveySubmissionId)
                .orElseThrow(() -> exception(SURVEY_SUBMISSION, ENTITY_NOT_FOUND, surveySubmissionId.toString()));

        if(!submission.getSurveyInstallation().getId().equals(surveyInstallationId))
            exception(SURVEY_SUBMISSION, ENTITY_NOT_FOUND, surveySubmissionId.toString());

        final Optional<UserDetailsModel> userDetails = submission.getUser() != null ? Optional.of(UserDetailsModel.FromSurveySubmissionUser(submission.getUser())) : Optional.empty();
        return processSubmission(submission, userDetails);
    }

    @Transactional
    private SurveySubmissionResultsDto processSubmission(SurveySubmission submission, Optional<UserDetailsModel> userDetails) throws IOException {

        //TODO: Load rules from DB for the current survey
//        Survey survey = submission.getSurveyInstallation().getSurvey();
//        survey.getId();
//        submission.getResponses().stream().flatMap(response -> response.getAnswers()).collect(Collectors.toList())

        SurveySubmissionResultsModel resultsModel = new SurveySubmissionResultsModel(submission);

        // Don't reprocess results
        if(submission.getResult() == null) {
            saveSubmissionResults(submission, resultsModel);
        }

        Optional<StoreApiClient> storeApiClient = storeFactory.getStoreApiClient(submission.getSurveyInstallation().getStoreType());

        if (storeApiClient.isPresent()) {
            try {
                Set<SurveyInstallationStoreSetting> storeSettings = submission.getSurveyInstallation().getSurveyInstallationStoreSettings();
                Set<PrescribedProduct> allAvailableProducts = storeApiClient.get().getProducts(storeSettings);

                if(allAvailableProducts.size() > 0)
                    resultsModel.filterAndAddPrescribedProducts(allAvailableProducts);
            } catch (Exception e) {
                log.error("An error occurred when retrieving or setting products on the results model", e);
                throw e;
            }
        }

        return SurveySubmissionResultsMapper.toSurveySubmissionResultsDto(modelMapper, resultsModel, userDetails);
    }

    @Override
    @Transactional
    public void processSurveySubmissionUserDetails(UUID surveySubmissionId, SurveySubmissionUserDto userDto) throws IOException {

        SurveySubmission submission = submissionRepository.findById(surveySubmissionId)
                .orElseThrow(() -> exception(SURVEY_SUBMISSION, ENTITY_NOT_FOUND, surveySubmissionId.toString()));

        if(!submission.getSurveyInstallation().getClient().isExternal()) {
            SurveySubmissionUser user = setSurveySubmissionUser(submission, userDto);
            submissionUserRepository.save(user);
        }

        final UserDetailsModel userDetails = UserDetailsModel.FromSurveySubmissionUserDto(userDto);
        SurveySubmissionResultsDto results = processSubmission(submission, Optional.of(userDetails));

        if (emailEnabled) {
            sendSubmissionConfirmationEmail(submission, userDetails);
        }

        CRMType crmType = submission.getSurveyInstallation().getCrmType();

        if(crmEnabled && crmType != null) {
            saveContactToCrm(crmType, submission, userDetails, results.getPrescribedProducts());
        }
    }

    private SurveySubmissionUser setSurveySubmissionUser(SurveySubmission submission, SurveySubmissionUserDto userDto) {
        SurveySubmissionUser user = submissionUserRepository.findByEmail(userDto.getEmail()).orElseGet(() -> {
            SurveySubmissionUser newUser = new SurveySubmissionUser();
            newUser.setEmail(userDto.getEmail());
            return newUser;
        });

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setOptedIn(userDto.isOptedIn());
        user.addSubmission(submission);

        return user;
    }

    private void saveContactToCrm(CRMType crmType, SurveySubmission submission, UserDetailsModel userDetails, Set<PrescribedProductDto> prescribedProducts) {
        try {
            Optional<CRMService> crm = crmFactory.getCRMService(crmType);

            if(crm.isPresent()) {
                crm.get().saveContactData(submission, userDetails, prescribedProducts);
            }
        }
        catch (Exception ex) {
            log.error(String.format("Error occured saving contact to CRM (%s) for submission id %s. Skipped", crmType, submission.getId()));
            // Continue if failed. Not Critical.
        }
    }

    private void sendSubmissionConfirmationEmail(SurveySubmission submission, UserDetailsModel userDetails) {

        SurveyInstallationSetting templateSetting = submission.getSurveyInstallation()
                .getSurveyInstallationSetting(SurveyInstallationSettingType.SUBMISSION_CONFIRMATION_EMAIL_TEMPLATE)
                .orElseThrow(() -> exception(SURVEY_SUBMISSION, EMAIL_SEND_ERROR, "No Html Template set"));

        Mail mail = new Mail(userDetails.getEmail(), "VitCheck Results Confirmation", templateSetting.getValue());

        String url = String.format("%s/?%s=%s&%s=%s",
                submission.getSurveyInstallation().getInstallationUrl(),
                VitcheckConsts.SUBMISSION_UID_QS,
                submission.getId(),
                VitcheckConsts.INSTALLATION_UID_QS,
                submission.getSurveyInstallation().getId());

        HashMap<String, Object> params = new HashMap<>();
        params.put("name", userDetails.getFullName());
        params.put("resultsUrl", url);
        mail.setModel(params);

        try {
            ClientSmtpManager smtpManager = ClientSmtpManager.create(submission.getSurveyInstallation().getClient().getId(), smtpEncryptionKey, smtpEncryptionIV);
            emailSender.sendHtmlEmail(mail, smtpManager);
        }
        catch (Exception e) {
            log.error("Error sending mail", e); // Continue happy path without email.
            throw exception(SURVEY_SUBMISSION, EMAIL_SEND_ERROR, e.toString());
        }
    }

    private void saveSubmissionResults(SurveySubmission submission, SurveySubmissionResultsModel resultsModel) {

        String decisionTableFilename = submission.getSurveyInstallation().getSurvey().getId() == VITCHECK_SURVEY_ID
                ? VITCHECK_CORE_RULES_FILENAME
                : VITDETECTOR_RULES_FILENAME;

        StatelessKieSession kieSession =
                surveyDecisionTableService.getDecisionTableKieSession(decisionTableFilename);

        log.info(surveyDecisionTableService.getDrlFromExcel((decisionTableFilename)));
        kieSession.execute(resultsModel);

        // Once decision table logic has run, additional interactions may have been calculated
        // so here we need to add those to the submission before it is saved.
        if(!resultsModel.getCalculatedInteractions().isEmpty())
        {
            submission.setCalculatedNutrientInteractions(resultsModel.getCalculatedInteractions());
        }


        SurveySubmissionResult result = new SurveySubmissionResult();
        result.setSurveySubmission(submission);
        result.setHasNotableInteractions(resultsModel.isHasNotableInteractions());
        Set<PrescribedProductType> prescribedProductTypes =
                PrescribedProductType.mapProductTypesToEntities(resultsModel.getPrescribedProductTypes());

        // filter iron out if a multivit has been prescribed
        boolean hasPrescribedMultiVit = prescribedProductTypes
                .stream()
                .anyMatch(p ->  p.getProductType() == ProductType.VEGETARIAN_FORMULA ||
                                p.getProductType() == ProductType.MENS_MULTIVIT ||
                                p.getProductType() == ProductType.MENS_MULTIVIT_50_PLUS ||
                                p.getProductType() == ProductType.WOMENS_MULTIVIT ||
                                p.getProductType() == ProductType.WOMENS_MULTIVIT_50_PLUS);

        if(hasPrescribedMultiVit) {
            prescribedProductTypes = prescribedProductTypes.stream().filter(p -> p.getProductType() != ProductType.IRON).collect(Collectors.toSet());
        }

        prescribedProductTypes.forEach(product -> product.setSurveySubmissionResult(result));

        result.setPrescribedProductTypes(new ArrayList<>(prescribedProductTypes));

        submission.setResult(result);

        submissionRepository.save(submission);
    }
}
