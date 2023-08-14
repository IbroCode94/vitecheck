package com.vitmedics.vitcheck.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.decisiontable.DecisionTableProviderImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Component;

@Component
public class SurveyDecisionTableServiceImpl implements SurveyDecisionTableService {

    private static final Logger log = LogManager.getLogger(SurveyDecisionTableServiceImpl.class);

    public StatelessKieSession getDecisionTableKieSession(String filePath) {
        return getKieSession(filePath);
    }

    private StatelessKieSession getKieSession(String filePath) {
        KieServices kieServices = KieServices.Factory.get();

        KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        kieFileSystem.write(ResourceFactory.newClassPathResource(filePath));

        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();

        KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());

        return kContainer.newStatelessKieSession();
    }

//    public static StatelessKieSession kieSessionFromByteArrayDecisionTable(byte[] data) {
//        DecisionTableConfiguration config = KnowledgeBuilderFactory.newDecisionTableConfiguration();
//        config.setInputType(DecisionTableInputType.XLS);
//
//        KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//        knowledgeBuilder.add(ResourceFactory.newByteArrayResource(data), ResourceType.DTABLE, config);
//
//        return knowledgeBuilder.newKieBase().newStatelessKieSession();
//    }

    public String getDrlFromExcel(String excelFile) {
        DecisionTableConfiguration configuration =
                KnowledgeBuilderFactory.newDecisionTableConfiguration();
        configuration.setInputType(DecisionTableInputType.XLS);

        Resource dt = ResourceFactory.newClassPathResource(excelFile, getClass());

        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();

        return decisionTableProvider.loadFromResource(dt, configuration);
    }

}
