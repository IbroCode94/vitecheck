package com.vitmedics.vitcheck.repository.client;

import com.vitmedics.vitcheck.model.entities.client.ClientSmtp;
import com.vitmedics.vitcheck.model.entities.survey.SurveySubmissionUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientSmtpRepository extends CrudRepository<ClientSmtp, String> {
    Optional<ClientSmtp> findByClientId(UUID clientId);
}
