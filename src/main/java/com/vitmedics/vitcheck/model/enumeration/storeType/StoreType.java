package com.vitmedics.vitcheck.model.enumeration.storeType;

public enum StoreType {
    SHOPIFY("SHOPIFY"),
    SHOPIFY_V2("SHOPIFY_V2");

    private String code;

    private StoreType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
