package com.vitmedics.vitcheck.dto.model.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vitmedics.vitcheck.model.enumeration.medicationDuration.MedicationDuration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveySubmissionResultMedicationDto {
    private long id;
    private String name;
    private MedicationDuration durationTaken;
    private List<MedicationInteractionDto> interactions;
}
