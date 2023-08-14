package com.vitmedics.vitcheck.model.entities.survey;

import lombok.Data;
import java.io.Serializable;

@Data
public class MedicationCountryMappingPK implements Serializable {
    private int medicationId;
    private String countryCode;
}
