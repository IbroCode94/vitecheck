package com.vitmedics.vitcheck.exception;

public enum ExceptionType {
    ENTITY_NOT_FOUND("not.found"),
    EMAIL_SEND_ERROR("error.sendmail"),
    DUPLICATE_ENTITY("duplicate"),
    ENTITY_EXCEPTION("exception");

    String value;

    ExceptionType(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
