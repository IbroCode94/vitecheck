package com.vitmedics.vitcheck.model.enumeration.storeSetting;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class StoreSettingConverter implements AttributeConverter<StoreSetting, String> {

    @Override
    public String convertToDatabaseColumn(StoreSetting storeSetting) {
        if (storeSetting == null) {
            return null;
        }
        return storeSetting.getCode();
    }

    @Override
    public StoreSetting convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(StoreSetting.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
