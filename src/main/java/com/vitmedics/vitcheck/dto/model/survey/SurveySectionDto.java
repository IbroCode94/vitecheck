package com.vitmedics.vitcheck.dto.model.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class SurveySectionDto {
    private int id;
    private String label;
    private List<SurveyQuestionDto> questions;
    private String introduction;
    private String icon;
    private int sortOrder;
}
