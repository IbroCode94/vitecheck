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
@Table(name = "survey_submission_response", uniqueConstraints = { @UniqueConstraint(columnNames = {"survey_submission_id", "survey_question_id"})})
public class SurveySubmissionResponse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch= FetchType.LAZY)
  @JoinColumn(name="survey_submission_id", nullable = false)
  private SurveySubmission surveySubmission;

  @ManyToOne(fetch= FetchType.LAZY)
  @JoinColumn(name="survey_question_id", nullable = false)
  private SurveyQuestion question;

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(
          name = "survey_submission_response_answer",
          joinColumns = @JoinColumn(name = "answer_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "survey_submission_response_id", referencedColumnName = "id"))
  private List<SurveyAnswer> answers;

  @Column(name="response_text_1")
  private String responseText1;

  @Column(name="response_text_2")
  private String responseText2;
}
