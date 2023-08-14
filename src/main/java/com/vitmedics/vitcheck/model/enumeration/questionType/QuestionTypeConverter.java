package com.vitmedics.vitcheck.model.enumeration.questionType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class QuestionTypeConverter implements AttributeConverter<QuestionType, String> {

    @Override
    public String convertToDatabaseColumn(QuestionType questionType) {
        if (questionType == null) {
            return null;
        }
        return questionType.getCode();
    }

    @Override
    public QuestionType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(QuestionType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
