package com.vitmedics.vitcheck.model.enumeration.crmSetting;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class CRMSettingConverter implements AttributeConverter<CRMSetting, String> {

    @Override
    public String convertToDatabaseColumn(CRMSetting crmSetting) {
        if (crmSetting == null) {
            return null;
        }
        return crmSetting.getCode();
    }

    @Override
    public CRMSetting convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(CRMSetting.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
