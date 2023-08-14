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
@Table(name = "nutrient")
public class Nutrient {
    @Id
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String summary;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "nutrient")
    private List<MedicationInteraction> medicationInteractions;

    @Column(name="function_in_body", length = 5000)
    private String functionInBody;

    @Column(name="food_sources", length = 5000)
    private String foodSources;

    @Column(name="other_info", length = 5000)
    private String otherInfo;

    @Column(name="important_for", length = 5000)
    private String importantFor;

    @Column(name="long_term_effects", length = 5000)
    private String longTermEffects;

    @Column(length = 5000)
    private String refs;

    private String alias;
}
