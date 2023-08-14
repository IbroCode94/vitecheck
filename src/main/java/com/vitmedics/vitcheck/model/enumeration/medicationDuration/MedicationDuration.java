package com.vitmedics.vitcheck.model.enumeration.medicationDuration;

public enum MedicationDuration {
    LESS_THAN_A_YEAR("LESS_THAN_A_YEAR"),
    BETWEEN_1_AND_2_YEARS("BETWEEN_1_AND_2_YEARS"),
    MORE_THAN_2_YEARS("MORE_THAN_2_YEARS");


    private String code;

    private MedicationDuration(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
