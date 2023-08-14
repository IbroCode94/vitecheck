package com.vitmedics.vitcheck.dto.model.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vitmedics.vitcheck.model.enumeration.resultsViewType.ResultsViewType;
import com.vitmedics.vitcheck.model.enumeration.surveyInstallationSettingType.SurveyInstallationSettingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyInstallationDto {
    private UUID id;
    private String clientName;
    private SurveyDto survey;
    private boolean emailConfirmationEnabled;
    private ResultsViewType resultsViewType;
    private Map<SurveyInstallationSettingType,Object> settings;
}
