package com.vitmedics.vitcheck.model.entities.survey;

public interface NutrientInteraction {

    Nutrient getNutrient();
    void setNutrient(Nutrient nutrient);

    Float getScore();
    void setScore(Float score);

    String getEvidence();
    void setEvidence(String evidence);
}
