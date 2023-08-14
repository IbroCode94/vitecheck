package com.vitmedics.vitcheck.model.enumeration.storeSetting;

/**
 * Settings that can be configured for a given store API implementation
 * Prefixed with the StoreType e.g. STORETYPE_SETTING1, STORETYPE_SETTING2
 */
public enum StoreSetting {

    SHOPIFY_BASE_URL("SHOPIFY_BASE_URL"),
    SHOPIFY_STOREFRONT_ACCESS_TOKEN("SHOPIFY_STOREFRONT_ACCESS_TOKEN");

    private String code;

    private StoreSetting(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
