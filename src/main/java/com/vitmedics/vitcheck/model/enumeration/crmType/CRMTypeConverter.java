package com.vitmedics.vitcheck.model.enumeration.crmType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class CRMTypeConverter implements AttributeConverter<CRMType, String> {

    @Override
    public String convertToDatabaseColumn(CRMType crmType) {
        if (crmType == null) {
            return null;
        }
        return crmType.getCode();
    }

    @Override
    public CRMType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(CRMType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
