package com.vitmedics.vitcheck.service;

import org.kie.api.runtime.StatelessKieSession;

public interface SurveyDecisionTableService {
    StatelessKieSession getDecisionTableKieSession(String filePath);
    String getDrlFromExcel(String excelFile);
}
