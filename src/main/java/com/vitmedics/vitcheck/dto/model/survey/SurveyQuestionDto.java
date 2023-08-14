package com.vitmedics.vitcheck.dto.model.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vitmedics.vitcheck.model.enumeration.questionType.QuestionType;
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
public class SurveyQuestionDto {
    private int id;
    private String label;
    private QuestionType questionType;
    private boolean required;
    private String validationRegex;
    private String tooltip;
    private String helpText;
    private Integer parentAnswerId;
    private int sortOrder;
    private List<SurveyAnswerDto> answers;

}
