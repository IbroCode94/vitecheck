package com.vitmedics.vitcheck.model.enumeration.resultsViewType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class ResultsViewTypeConverter implements AttributeConverter<ResultsViewType, String> {

    @Override
    public String convertToDatabaseColumn(ResultsViewType resultsViewType) {
        if (resultsViewType == null) {
            return null;
        }
        return resultsViewType.getCode();
    }

    @Override
    public ResultsViewType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(ResultsViewType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
