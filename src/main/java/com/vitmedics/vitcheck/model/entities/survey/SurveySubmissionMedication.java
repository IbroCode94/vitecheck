package com.vitmedics.vitcheck.model.entities.survey;

import com.vitmedics.vitcheck.model.enumeration.medicationDuration.MedicationDuration;
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
@Table(name = "survey_submission_medication")
public class SurveySubmissionMedication {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "survey_submission_id", nullable = false)
  private SurveySubmission surveySubmission;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "medication_id", nullable = false)
  private Medication medication;

  @Column(name="duration_taken", nullable = false)
  private MedicationDuration durationTaken;
}
