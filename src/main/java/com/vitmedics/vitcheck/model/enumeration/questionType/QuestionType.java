package com.vitmedics.vitcheck.model.enumeration.questionType;

public enum QuestionType {
    SINGLE_SELECT("SINGLE_SELECT"),
    MULTI_SELECT("MULTI_SELECT"),
    TEXT("TEXT"),
    NUMBER("NUMBER"),
    WEIGHT("WEIGHT"),
    HEIGHT("HEIGHT");

    private String code;

    private QuestionType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
