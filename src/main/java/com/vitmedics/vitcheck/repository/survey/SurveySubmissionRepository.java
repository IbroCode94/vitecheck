package com.vitmedics.vitcheck.repository.survey;

import com.vitmedics.vitcheck.model.entities.survey.SurveySubmission;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SurveySubmissionRepository extends CrudRepository<SurveySubmission, UUID> {

}
