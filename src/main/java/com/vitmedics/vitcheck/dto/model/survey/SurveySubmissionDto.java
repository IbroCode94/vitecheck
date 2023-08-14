package com.vitmedics.vitcheck.dto.model.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveySubmissionDto {

    private UUID id;
    private SurveySubmissionUserDto user;

    @Valid
    private List<SurveySubmissionMedicationDto> medications;

    @Valid
    private List<SurveySubmissionResponseDto> responses;

    private SurveySubmissionResultsDto result;
}
