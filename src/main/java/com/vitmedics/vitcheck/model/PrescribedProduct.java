package com.vitmedics.vitcheck.model;

import com.vitmedics.vitcheck.model.enumeration.productType.ProductType;
import lombok.*;

import java.util.HashMap;

@Getter
@EqualsAndHashCode
@ToString
public class PrescribedProduct {

    private ProductType productType;
    private String externalId;
    private String name;

    @Setter
    private String description;

    private HashMap<String, String> properties;

    public PrescribedProduct(ProductType productType, String externalId, String name) {
        this.productType = productType;
        this.externalId = externalId;
        this.name = name;

        properties = new HashMap<>();
    }

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }
}
