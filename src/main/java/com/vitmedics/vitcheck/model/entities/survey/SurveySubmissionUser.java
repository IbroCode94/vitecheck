package com.vitmedics.vitcheck.model.entities.survey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "survey_submission_user")
public class SurveySubmissionUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "opted_in", nullable = false)
  private boolean optedIn;

  @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<SurveySubmission> surveySubmissions = new ArrayList<>();

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public void addSubmission(SurveySubmission submission) {
    surveySubmissions.add(submission);
    submission.setUser(this);
  }
}
