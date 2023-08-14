package com.vitmedics.vitcheck.dto.model.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveySubmissionResultsDto {
    private UUID surveySubmissionId;
    private SurveySubmissionUserDto user;
    private boolean hasNotableInteractions;
    private List<SurveySubmissionResultMedicationDto> medications;
    private List<NutrientInteractionDto> aggregatedNutrientInteractions;
    private Set<PrescribedProductDto> prescribedProducts;
    private List<TailoredResponseDto> tailoredResponses;
}
