package com.vitmedics.vitcheck.integration.crm;

import com.vitmedics.vitcheck.dto.model.survey.PrescribedProductDto;
import com.vitmedics.vitcheck.model.UserDetailsModel;
import com.vitmedics.vitcheck.model.entities.survey.SurveySubmission;

import java.util.Set;

public interface CRMService {
    void saveContactData(SurveySubmission submission, UserDetailsModel userDetails, Set<PrescribedProductDto> prescribedProducts);
}
