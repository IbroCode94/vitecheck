package com.vitmedics.vitcheck.model.entities.survey;

import lombok.Data;
import java.io.Serializable;

@Data
public class MedicationInteractionPK implements Serializable {
    private int nutrientId;
    private int medicationId;
}