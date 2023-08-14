package com.vitmedics.vitcheck.model.enumeration.surveyInstallationSettingType;

public enum SurveyInstallationSettingType {

    RESULTS_VIEW_TYPE("RESULTS_VIEW_TYPE"), // the type of control to render scores with - Enum
    HEAT_SCALE_RESULTS_CEILING("HEAT_SCALE_RESULTS_CEILING"), // Set a max for score total for a nutrient - int
    HEAT_SCALE_RESULTS_MID("HEAT_SCALE_RESULTS_MID"), // set a mid for score total for a nutrient - int
    NUTRIENT_ID_MEDICATION_FILTER("NUTRIENT_ID_MEDICATION_FILTER"), // configure a comma separated list of ids to filter medications on - comma separated list of id
    AGGREGATE_MEDICATION_NUTRIENT_SCORES("AGGREGATE_MEDICATION_NUTRIENT_SCORES"), // Aggregate medication scores per nutrient and impose max of 3 - bool
    CHECKOUT_ENABLED("CHECKOUT_ENABLED"),
    PRINT_PRESCRIPTION_ENABLED("PRINT_PRESCRIPTION_ENABLED"),
    SUBMISSION_SENDER_ADDRESS("SUBMISSION_SENDER_ADDRESS"), // DEPRECATED -> see ClientSmtp instead
    SUBMISSION_CONFIRMATION_EMAIL_TEMPLATE("SUBMISSION_CONFIRMATION_EMAIL_TEMPLATE");


    private String code;

    private SurveyInstallationSettingType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
