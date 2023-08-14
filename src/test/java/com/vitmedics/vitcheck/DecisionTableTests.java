//package com.vitmedics.vitcheck;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import com.vitmedics.vitcheck.model.SurveySubmissionResultsModel;
//import com.vitmedics.vitcheck.model.entities.survey.*;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.drools.decisiontable.DecisionTableProviderImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.kie.api.KieServices;
//import org.kie.api.builder.KieBuilder;
//import org.kie.api.builder.KieFileSystem;
//import org.kie.api.builder.KieModule;
//import org.kie.api.builder.KieRepository;
//import org.kie.api.io.Resource;
//import org.kie.api.runtime.KieContainer;
//import org.kie.api.runtime.StatelessKieSession;
//import org.kie.internal.builder.DecisionTableConfiguration;
//import org.kie.internal.builder.DecisionTableInputType;
//import org.kie.internal.builder.KnowledgeBuilderFactory;
//import org.kie.internal.io.ResourceFactory;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//public class DecisionTableTests {
//
//    private static final Logger log = LogManager.getLogger(DecisionTableTests.class);
//
//    private static final String VITCHECK_CORE_RULES_FILENAME = "decision-tables/vitcheck-core-decision-table.xls";
//
////    @Test
////    public void vegetarian_response_prescribes_vegformula_and_veganOmega3() {
////        log.info(getDrlFromExcel(VITCHECK_CORE_RULES_FILENAME));
////        StatelessKieSession kieSession = getKieSession(VITCHECK_CORE_RULES_FILENAME);
////
////        List<SurveySubmissionResponse> responses = new ArrayList<>();
////        responses.add(getVegetarianResponse(true));
////
////        SurveySubmission submission = new SurveySubmission();
////        submission.setResponses(responses);
////
////        submission.setSurveyInstallation(getSurveyInstallationConfig());
////        submission.setMedications(new ArrayList<>());
////
////        SurveySubmissionResultsModel model = new SurveySubmissionResultsModel(submission);
////
////        kieSession.execute(model);
////
////        assertEquals(2, model.getPrescribedProducts().size());
////    }
//
//    private SurveyInstallation getSurveyInstallationConfig() {
//        SurveyInstallation installation = new SurveyInstallation();
//        installation.setSurveyInstallationSettings(new HashSet<>());
//        return installation;
//    }
//
//    private SurveySubmissionResponse getMetricHeightResponse(String metres) {
//        SurveySubmissionResponse response = new SurveySubmissionResponse();
//        SurveyQuestion question = new SurveyQuestion().setId(10);
//        response.setQuestion(question);
//        response.setResponseText1(metres);
//
//        return response;
//    }
//
//    private SurveySubmissionResponse getMetricWeightResponse(String kgs) {
//        SurveySubmissionResponse response = new SurveySubmissionResponse();
//        SurveyQuestion question = new SurveyQuestion().setId(9);
//        response.setQuestion(question);
//        response.setResponseText1(kgs);
//        return response;
//    }
//
//    private SurveySubmissionResponse getVegetarianResponse(boolean isVegetarian) {
//        SurveySubmissionResponse response = new SurveySubmissionResponse();
//        SurveyQuestion question = new SurveyQuestion().setId(8);
//        response.setQuestion(question);
//
//        SurveyAnswer answer = new SurveyAnswer();
//        answer.setId(isVegetarian ? 19 : 20);
//        response.setAnswers(List.of(answer));
//
//        return response;
//    }
//
//
//
//
//    private StatelessKieSession getKieSession(String fileName) {
//        KieServices kieServices = KieServices.Factory.get();
//
//        KieRepository kieRepository = kieServices.getRepository();
//        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
//        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//
//        kieFileSystem.write(ResourceFactory.newClassPathResource(fileName));
//
//        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
//        kb.buildAll();
//        KieModule kieModule = kb.getKieModule();
//
//        KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());
//
//        return kContainer.newStatelessKieSession();
//    }
//
//    /*
//     * Can be used for debugging
//     */
//    public String getDrlFromExcel(String excelFile) {
//        DecisionTableConfiguration configuration =
//                KnowledgeBuilderFactory.newDecisionTableConfiguration();
//        configuration.setInputType(DecisionTableInputType.XLS);
//
//        Resource dt = ResourceFactory.newClassPathResource(excelFile, getClass());
//
//        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();
//
//        return decisionTableProvider.loadFromResource(dt, configuration);
//    }
//}
