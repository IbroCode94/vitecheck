package com.vitmedics.vitcheck.model.entities.survey;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vitmedics.vitcheck.model.enumeration.questionType.QuestionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "survey_question")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SurveyQuestion {
    @Id
    @SequenceGenerator(name="survey_question_id_seq", sequenceName = "survey_question_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "survey_question_id_seq")
    private int id;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private SurveySection section;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL)
    private List<SurveyAnswer> answers;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parent_answer_id")
    private SurveyAnswer parentAnswer;

    @Column(name="sort_order", nullable = false)
    private int sortOrder;

    @Column(name="question_type", nullable = false)
    private QuestionType questionType;

    @Column(nullable = false)
    private boolean required;

    @Column(name = "validation_regex")
    private String validationRegex;

    @Column(nullable = false)
    private String label;

    @Column(length=500)
    private String tooltip;

    @Column(name="help_text", length=5000)
    private String helpText;

    @Column(name="archived")
    private LocalDateTime archived;
}
