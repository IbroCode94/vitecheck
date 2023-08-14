package com.vitmedics.vitcheck.controller.v1.api;

import com.vitmedics.vitcheck.dto.APIResponse;
import com.vitmedics.vitcheck.dto.model.survey.SurveyInstallationDto;
import com.vitmedics.vitcheck.service.SurveyInstallationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SurveyInstallationsControllerImpl implements SurveyInstallationsController {

    @Autowired
    private SurveyInstallationService surveyInstallationService;

    public APIResponse getSurveyInstallations() {
        return APIResponse
                .ok()
                .setPayload(surveyInstallationService.getServiceInstallations());
    }

    public APIResponse getSurveyInstallationById(final UUID id) {
        SurveyInstallationDto dto = surveyInstallationService.getServiceInstallationById(id);
        return APIResponse
                .ok()
                .setPayload(dto);
    }

}
