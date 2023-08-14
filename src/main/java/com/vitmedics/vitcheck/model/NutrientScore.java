package com.vitmedics.vitcheck.model;

import com.vitmedics.vitcheck.model.entities.survey.Nutrient;
import com.vitmedics.vitcheck.model.entities.survey.NutrientInteraction;

import java.util.Objects;

public class NutrientScore implements NutrientInteraction {
    private Nutrient nutrient;
    private Float score;
    private String evidence;

    public NutrientScore(Nutrient nutrient, Float score) {
        this.nutrient = nutrient;
        this.score = score;
    }

    @Override
    public Nutrient getNutrient() {
        return nutrient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NutrientScore that = (NutrientScore) o;
        return nutrient.equals(that.nutrient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nutrient);
    }

    @Override
    public void setNutrient(Nutrient nutrient) {
        this.nutrient = nutrient;
    }

    @Override
    public Float getScore() {
        return score;
    }

    @Override
    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public String getEvidence() {
        return evidence;
    }

    @Override
    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }
}
