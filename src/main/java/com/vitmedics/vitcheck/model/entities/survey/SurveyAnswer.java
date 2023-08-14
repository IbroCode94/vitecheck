package com.vitmedics.vitcheck.model.entities.survey;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "survey_answer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SurveyAnswer {
    @Id
    @SequenceGenerator(name="survey_answer_id_seq", sequenceName = "survey_answer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "survey_answer_id_seq")
    private int id;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private SurveyQuestion question;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="answer")
    private List<SurveyAnswerInteraction> interactions;

    @Column(nullable = false)
    private String name;

    @Column(name="sort_order", nullable = false)
    private int sortOrder;

    @Column(name="is_other", nullable = false)
    private boolean isOther = false;

    @Column(nullable = false)
    private String label;

    @Column(name="additional_info", length=3000)
    private String additionalInfo;

    @Column(name="additional_info_evidence", length=3000)
    private String additionalInfoEvidence;
}
