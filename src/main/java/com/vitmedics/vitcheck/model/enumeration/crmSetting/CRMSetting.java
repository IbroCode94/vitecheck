package com.vitmedics.vitcheck.model.enumeration.crmSetting;

public enum CRMSetting {
    MAILCHIMP_API_KEY("MAILCHIMP_API_KEY"),
    MAILCHIMP_AUDIENCE_ID("MAILCHIMP_AUDIENCE_ID"),
    MAILCHIMP_DATA_FIELD_MEDS_KEY("MAILCHIMP_DATA_FIELD_MEDS_KEY"),
    MAILCHIMP_DATA_FIELD_QUESTION_PREFIX("MAILCHIMP_DATA_FIELD_QUESTION_PREFIX"),
    KLAVIYO_API_KEY("KLAVIYO_API_KEY");

    private String code;

    private CRMSetting(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
