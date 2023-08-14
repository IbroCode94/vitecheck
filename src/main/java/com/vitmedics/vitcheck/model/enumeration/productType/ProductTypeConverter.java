package com.vitmedics.vitcheck.model.enumeration.productType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class ProductTypeConverter implements AttributeConverter<ProductType, String> {

    @Override
    public String convertToDatabaseColumn(ProductType productType) {
        if (productType == null) {
            return null;
        }
        return productType.getCode();
    }

    @Override
    public ProductType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(ProductType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
