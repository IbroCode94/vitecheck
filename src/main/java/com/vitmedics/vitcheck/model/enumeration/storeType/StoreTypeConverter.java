package com.vitmedics.vitcheck.model.enumeration.storeType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class StoreTypeConverter implements AttributeConverter<StoreType, String> {

    @Override
    public String convertToDatabaseColumn(StoreType storeType) {
        if (storeType == null) {
            return null;
        }
        return storeType.getCode();
    }

    @Override
    public StoreType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(StoreType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
