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
@Table(name = "medication",
        indexes = {
            @Index(name="ix_name", columnList = "name")
        })
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "medication")
    private List<MedicationInteraction> medicationInteractions;
}
