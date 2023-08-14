package com.vitmedics.vitcheck.model.enumeration.surveyInstallationSettingType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class SurveyInstallationSettingTypeConverter implements AttributeConverter<SurveyInstallationSettingType, String> {

    @Override
    public String convertToDatabaseColumn(SurveyInstallationSettingType surveyInstallationSettingType) {
        if (surveyInstallationSettingType == null) {
            return null;
        }
        return surveyInstallationSettingType.getCode();
    }

    @Override
    public SurveyInstallationSettingType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(SurveyInstallationSettingType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
