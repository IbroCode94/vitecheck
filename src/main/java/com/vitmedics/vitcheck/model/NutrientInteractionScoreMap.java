package com.vitmedics.vitcheck.model;


import com.vitmedics.vitcheck.model.entities.survey.Nutrient;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NutrientInteractionScoreMap implements Iterable<Map.Entry<Integer,NutrientScore>> {

    private HashMap<Integer,NutrientScore> map;
    private int nutrientScoreCeiling = Integer.MAX_VALUE;

    public NutrientInteractionScoreMap() {
        this.map = new HashMap<>();
    }

    public NutrientInteractionScoreMap(int nutrientScoreCeiling) {
        this.map = new HashMap<>();
    }

    @Override
    public Iterator<Map.Entry<Integer,NutrientScore>> iterator() {
        return map.entrySet().iterator();
    }

    public Float getNutrientScore(Integer nutrientId) {
        return this.map.get(nutrientId).getScore();
    }

    public NutrientScore put(Nutrient nutrient, float score, String evidence) {

        NutrientScore entry = this.map.get(nutrient.getId());

        if (entry == null) {
            entry = new NutrientScore(nutrient, score);

            if( Strings.isNotEmpty(evidence))
                entry.setEvidence(evidence);
        }
        else {
            float existingScore = entry.getScore();
            float newScore = Math.min(existingScore + score, nutrientScoreCeiling);
            entry.setScore(newScore);
        }

        return map.put(nutrient.getId(), entry);
    }

    /**
     * Will increment the score for the given nutrientId and if not present will create
     * a Stub nutrient entity to track the score
     * @param nutrientId
     * @param score
     * @return the updated nutrient or null if not present
     */
    public NutrientScore put(int nutrientId, float score) {

        NutrientScore entry = this.map.get(nutrientId);

        if(entry == null) {
            Nutrient nutrientStub = new Nutrient();
            nutrientStub.setId(nutrientId);

            entry = new NutrientScore(nutrientStub, score);
            map.put(nutrientId, entry);
        }
        else {
            float existingScore = entry.getScore();
            float newScore = Math.min(existingScore + score, nutrientScoreCeiling);
            entry.setScore(newScore);
        }

        return entry;
    }
}
