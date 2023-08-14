package com.vitmedics.vitcheck.model.entities.survey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "calculated_nutrient_interaction", uniqueConstraints = { @UniqueConstraint(columnNames = {"survey_submission_id", "nutrient_id"})})
public class CalculatedNutrientInteraction implements NutrientInteraction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch= FetchType.LAZY)
  @JoinColumn(name="survey_submission_id", nullable = false)
  private SurveySubmission surveySubmission;

  @ManyToOne(fetch= FetchType.LAZY)
  @JoinColumn(name="nutrient_id", nullable = false)
  private Nutrient nutrient;

  @Column(name="score", nullable = false)
  private Float score;

  @Column(name="evidence")
  private String evidence;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public SurveySubmission getSurveySubmission() {
    return surveySubmission;
  }

  public void setSurveySubmission(SurveySubmission surveySubmission) {
    this.surveySubmission = surveySubmission;
  }

  @Override
  public Nutrient getNutrient() {
    return nutrient;
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
