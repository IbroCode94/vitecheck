package com.vitmedics.vitcheck.model.entities.survey;

import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "medication_interaction")
@IdClass(MedicationInteractionPK.class)
public class MedicationInteraction implements NutrientInteraction {

    @Id
    @Column(name="nutrient_id", nullable = false, insertable = false, updatable = false)
    private int nutrientId;

    @Id
    @Column(name="medication_id", nullable = false, insertable = false, updatable = false)
    private int medicationId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "nutrient_id", nullable = false)
    private Nutrient nutrient;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @Column(nullable = false)
    private Float score;

    @Column(length = 5000)
    private String evidence;

    public MedicationInteraction(Medication medication, Nutrient nutrient, Float score) {
        this.medication = medication;
        this.nutrient = nutrient;
        this.score = score;
    }

    public int getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(int nutrientId) {
        this.nutrientId = nutrientId;
    }

    public int getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }

    @Override
    public Nutrient getNutrient() {
        return nutrient;
    }

    @Override
    public void setNutrient(Nutrient nutrient) {
        this.nutrient = nutrient;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    @Override
    @Column(precision = 2)
    public Float getScore() {
        return score;
    }

    @Override
    public void setScore(Float score) {
        this.score = score;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

}
