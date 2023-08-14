package com.vitmedics.vitcheck.model.entities.survey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "survey_submission")
public class SurveySubmission {
  @Id
  private UUID id = UUID.randomUUID();

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "survey_installation_id", nullable = false)
  private SurveyInstallation surveyInstallation;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name="survey_submission_user_id", nullable = true)
  private SurveySubmissionUser user;

  @OneToMany(fetch=FetchType.LAZY, mappedBy = "surveySubmission", cascade = CascadeType.ALL)
  private List<SurveySubmissionResponse> responses;

  @OneToMany(fetch=FetchType.LAZY, mappedBy = "surveySubmission", cascade = CascadeType.ALL)
  private List<SurveySubmissionMedication> medications;

  @OneToMany(fetch=FetchType.LAZY, mappedBy = "surveySubmission", cascade = CascadeType.ALL)
  private List<CalculatedNutrientInteraction> calculatedNutrientInteractions;

  @Column(nullable = false)
  private LocalDateTime created = LocalDateTime.now();

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private SurveySubmissionResult result;
}
