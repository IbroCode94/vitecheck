package com.vitmedics.vitcheck.model.enumeration.medicationDuration;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class MedicationDurationConverter implements AttributeConverter<MedicationDuration, String> {

    @Override
    public String convertToDatabaseColumn(MedicationDuration medicationDuration) {
        if (medicationDuration == null) {
            return null;
        }
        return medicationDuration.getCode();
    }

    @Override
    public MedicationDuration convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(MedicationDuration.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
