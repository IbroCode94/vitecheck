package com.vitmedics.vitcheck.model.entities.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "survey_decision_table")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SurveyDecisionTable {
    @Id
    private int id;

    @Column(nullable = false)
    private String fileName;

    @ManyToOne(fetch=FetchType.LAZY)
    private Survey survey;

    @Lob
    @JsonIgnore
    private byte[] rulesData;

    @Column(nullable = false)
    private String version;

    @JsonIgnore
    private boolean active;

    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime archived;
}
