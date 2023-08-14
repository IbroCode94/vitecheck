package com.vitmedics.vitcheck.model.entities.survey;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "survey")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Survey {
    @Id
    @SequenceGenerator(name="survey_id_seq", sequenceName = "survey_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "survey_id_seq")
    private int id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(length=2000)
    private String introduction;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="survey")
    @JsonBackReference
    private List<SurveyInstallation> surveyInstallations;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "survey", cascade = CascadeType.ALL)
    private List<SurveySection> sections;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="survey")
    @JsonBackReference
    @JsonIgnore
    private List<SurveyDecisionTable> decisionTables;

    @Column(nullable = false)
    private String version;

    @JsonIgnore
    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime archived;

    public void sortAndFilterSurveyElements() {
        this.getSections().sort(Comparator.comparing(SurveySection::getSortOrder));

        this.getSections().forEach(section -> {
            section.getQuestions().sort(Comparator.comparing(SurveyQuestion::getSortOrder));

            // Remove archived questions - lazy effort for now, TODO: filter out in DB query
            List<SurveyQuestion> qs = section.getQuestions().stream().filter(q -> q.getArchived() == null).collect(Collectors.toList());
            section.setQuestions(qs);

            section.getQuestions().forEach(question -> {
                question.getAnswers().sort(Comparator.comparing(SurveyAnswer::getSortOrder));
            });
        });
    }
}
