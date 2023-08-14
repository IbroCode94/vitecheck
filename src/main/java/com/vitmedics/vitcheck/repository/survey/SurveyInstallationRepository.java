package com.vitmedics.vitcheck.repository.survey;

import com.vitmedics.vitcheck.model.entities.survey.SurveyInstallation;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SurveyInstallationRepository extends CrudRepository<SurveyInstallation, UUID> {

}
