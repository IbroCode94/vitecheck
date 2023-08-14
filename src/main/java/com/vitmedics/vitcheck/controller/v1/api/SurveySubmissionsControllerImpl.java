package com.vitmedics.vitcheck.controller.v1.api;

import com.vitmedics.vitcheck.controller.v1.request.SetSurveySubmissionUserRequest;
import com.vitmedics.vitcheck.controller.v1.request.SurveySubmissionRequest;
import com.vitmedics.vitcheck.dto.APIResponse;
import com.vitmedics.vitcheck.dto.model.survey.SurveySubmissionResultsDto;
import com.vitmedics.vitcheck.service.SurveySubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
public class SurveySubmissionsControllerImpl implements SurveySubmissionsController{

    @Autowired
    private SurveySubmissionService submissionService;

    public APIResponse createSurveySubmission(final SurveySubmissionRequest request) {

        UUID submissionId = this.submissionService.saveSubmission(request.getSurveyInstallationId(), request.getSubmission());
        return APIResponse
                .ok()
                .setPayload(submissionId);
    }

    public APIResponse getSurveySubmissionResults(
           final UUID surveySubmissionId,
           final UUID surveyInstallationId) throws IOException {

        SurveySubmissionResultsDto results = this.submissionService.processSubmission(surveySubmissionId, surveyInstallationId);

        return APIResponse
                .ok()
                .setPayload(results);
    }

    public APIResponse setSurveySubmissionUser(final UUID surveySubmissionId, final SetSurveySubmissionUserRequest request) throws IOException {

        this.submissionService.processSurveySubmissionUserDetails(surveySubmissionId, request.getUser());

        return APIResponse
                .ok()
                .setPayload(true);
    }

}
