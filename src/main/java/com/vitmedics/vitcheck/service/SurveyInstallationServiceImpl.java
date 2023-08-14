package com.vitmedics.vitcheck.service;

import com.vitmedics.vitcheck.dto.mapper.SurveyInstallationMapper;
import com.vitmedics.vitcheck.dto.model.survey.SurveyInstallationDto;
import com.vitmedics.vitcheck.model.entities.survey.SurveyInstallation;
import com.vitmedics.vitcheck.repository.survey.SurveyInstallationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.vitmedics.vitcheck.exception.EntityType.SURVEY_INSTALLATION;
import static com.vitmedics.vitcheck.exception.ExceptionType.*;

@Component
public class SurveyInstallationServiceImpl extends BaseService implements SurveyInstallationService {

    private static final Logger log = LogManager.getLogger(SurveyInstallationServiceImpl.class);

    @Autowired
    private SurveyInstallationRepository surveyInstallationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SurveyInstallationDto> getServiceInstallations() {
        return StreamSupport
                .stream(surveyInstallationRepository.findAll().spliterator(), false)
                .map(installation -> sortFilterAndMapSurveyInstallation(installation))
                .collect(Collectors.toList());
    }

    @Override
    public SurveyInstallationDto getServiceInstallationById(UUID id) {

        SurveyInstallation installation = surveyInstallationRepository.findById(id)
                .orElseThrow(() -> exception(SURVEY_INSTALLATION, ENTITY_NOT_FOUND, id.toString()));

        return sortFilterAndMapSurveyInstallation(installation);
    }

    private SurveyInstallationDto sortFilterAndMapSurveyInstallation(SurveyInstallation installation) {
        installation.getSurvey().sortAndFilterSurveyElements();
        return SurveyInstallationMapper.toSurveyInstallationDto(installation);
    }
}
