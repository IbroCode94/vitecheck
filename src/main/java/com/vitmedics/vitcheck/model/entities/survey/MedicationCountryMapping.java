package com.vitmedics.vitcheck.model.entities.survey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "country_medication", uniqueConstraints = { @UniqueConstraint(columnNames = {"medication_id", "country_code"})})
@IdClass(MedicationCountryMappingPK.class)
public class MedicationCountryMapping {

    @Id
    @Column(name="medication_id", insertable = false, updatable = false)
    private int medicationId;

    @Id
    @Column(name="country_code")
    private String countryCode;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @Column(nullable = false)
    private String label;
}
