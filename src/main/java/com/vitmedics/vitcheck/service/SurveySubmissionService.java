package com.vitmedics.vitcheck.service;

import com.vitmedics.vitcheck.dto.model.survey.SurveySubmissionDto;
import com.vitmedics.vitcheck.dto.model.survey.SurveySubmissionResultsDto;
import com.vitmedics.vitcheck.dto.model.survey.SurveySubmissionUserDto;

import java.io.IOException;
import java.util.UUID;

public interface SurveySubmissionService {
    UUID saveSubmission(UUID surveyInstallationId, SurveySubmissionDto submission);
    SurveySubmissionResultsDto processSubmission(UUID surveySubmissionId, UUID surveyInstallationId) throws IOException;

    void processSurveySubmissionUserDetails(UUID surveySubmissionId, SurveySubmissionUserDto user) throws IOException;
}

