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
@Table(name = "survey_section")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SurveySection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="survey_id", nullable = false, updatable = true, insertable = true)
    private Survey survey;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "section",cascade = CascadeType.PERSIST)
    private List<SurveyQuestion> questions;

    @Column(nullable = false)
    private String label;

    @Column(length=2000)
    private String introduction;

    @Column(length=100)
    private String icon;

    @Column(name="sort_order", nullable = false)
    private int sortOrder;
}
