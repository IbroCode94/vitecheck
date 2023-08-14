package com.vitmedics.vitcheck.model.entities.client;


import com.vitmedics.vitcheck.model.entities.survey.SurveyInstallation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "client")
public class Client {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean enabled = false;

    @Column(nullable = false)
    private boolean external = true;

    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    private LocalDateTime updated;

    private LocalDateTime archived;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<SurveyInstallation> surveyInstallations;
}

