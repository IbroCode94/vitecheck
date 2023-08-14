package com.vitmedics.vitcheck.model.entities.survey;

import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.NotImplementedException;

import javax.persistence.*;

@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "survey_answer_interaction")
@IdClass(SurveyAnswerInteractionPK.class)
public class SurveyAnswerInteraction implements NutrientInteraction {

    @Id
    @Column(name="nutrient_id", nullable = false, insertable = false, updatable = false)
    private int nutrientId;

    @Id
    @Column(name="answer_id", nullable = false, insertable = false, updatable = false)
    private int answerId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "nutrient_id", nullable = false)
    private Nutrient nutrient;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    private SurveyAnswer answer;

    @Column(nullable = false)
    private Float score;

    public int getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(int nutrientId) {
        this.nutrientId = nutrientId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    @Override
    public Nutrient getNutrient() {
        return nutrient;
    }

    @Override
    public void setNutrient(Nutrient nutrient) {
        this.nutrient = nutrient;
    }

    public SurveyAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(SurveyAnswer answer) {
        this.answer = answer;
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

    @Override
    public String getEvidence() {
        return null;
    }

    @Override
    public void setEvidence(String evidence) {
        throw new NotImplementedException();
    }
}
