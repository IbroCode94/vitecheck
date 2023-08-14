package com.vitmedics.vitcheck.service;

import com.vitmedics.vitcheck.dto.model.survey.SurveyInstallationDto;

import java.util.List;
import java.util.UUID;

public interface SurveyInstallationService {
    List<SurveyInstallationDto> getServiceInstallations();
    SurveyInstallationDto getServiceInstallationById(UUID id);
}

