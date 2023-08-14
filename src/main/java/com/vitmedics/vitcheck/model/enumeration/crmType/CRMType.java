package com.vitmedics.vitcheck.model.enumeration.crmType;

public enum CRMType {
    MAILCHIMP("MAILCHIMP"),
    KLAVIYO("KLAVIYO");


    private String code;

    private CRMType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
