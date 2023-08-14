package com.vitmedics.vitcheck.model.enumeration.productType;

public enum ProductType {

    MENS_MULTIVIT("MENS_MULTIVIT"),
    WOMENS_MULTIVIT("WOMENS_MULTIVIT"),
    VEGETARIAN_FORMULA("VEGETARIAN_FORMULA"),
    OMEGA3("OMEGA3"),
    VITAMIN_B12("VITAMIN_B12"),
    MAGNESIUM("MAGNESIUM"),
    COEQ10("COEQ10"),
    PREGNANCY_FORMULA("PREGNANCY_FORMULA"),
    CALCIUM("CALCIUM"),
    VITAMIN_D("VITAMIN_D"),
    IRON("IRON"),
    MENS_MULTIVIT_50_PLUS("MENS_MULTIVIT_50_PLUS"),
    WOMENS_MULTIVIT_50_PLUS("WOMENS_MULTIVIT_50_PLUS"),
    PROBIOTIC("PROBIOTIC"),
    FOLIC_ACID("FOLIC_ACID"),
    VEGAN_OMEGA3("VEGAN_OMEGA3"),
    SELENIUM_AND_ZINC("SELENIUM_AND_ZINC"),
    VITAMIN_C("VITAMIN_C"),
    VITAMIN_D_PLUS("VITAMIN_D_PLUS");

    private String code;

    private ProductType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
