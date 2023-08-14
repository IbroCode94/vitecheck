package com.vitmedics.vitcheck.repository.survey;

import com.vitmedics.vitcheck.model.entities.survey.SurveySubmissionUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SurveySubmissionUserRepository extends CrudRepository<SurveySubmissionUser, Long> {
    Optional<SurveySubmissionUser> findByEmail(String email);
}
