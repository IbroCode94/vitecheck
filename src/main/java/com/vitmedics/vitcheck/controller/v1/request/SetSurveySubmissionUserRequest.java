package com.vitmedics.vitcheck.controller.v1.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vitmedics.vitcheck.dto.model.survey.SurveySubmissionDto;
import com.vitmedics.vitcheck.dto.model.survey.SurveySubmissionUserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetSurveySubmissionUserRequest {

    @Valid
    @NotNull(message = "{constraints.NotNull.message}")
    private SurveySubmissionUserDto user;
}
