package com.vitmedics.vitcheck.model.entities.survey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "survey_submission_result")
public class SurveySubmissionResult {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private boolean hasNotableInteractions;

  @OneToOne(fetch= FetchType.LAZY)
  @JoinColumn(name="survey_submission_id", nullable = false)
  private SurveySubmission surveySubmission;

  @OneToMany(fetch= FetchType.LAZY, mappedBy="surveySubmissionResult", cascade = CascadeType.ALL)
  private List<PrescribedProductType> prescribedProductTypes;
}
