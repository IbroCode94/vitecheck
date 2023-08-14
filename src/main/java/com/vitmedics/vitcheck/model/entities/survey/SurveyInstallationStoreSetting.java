package com.vitmedics.vitcheck.model.entities.survey;

import com.vitmedics.vitcheck.model.enumeration.storeSetting.StoreSetting;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "survey_installation_store_setting", uniqueConstraints = { @UniqueConstraint(columnNames = {"survey_installation_id", "store_setting"})})
public class SurveyInstallationStoreSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "survey_installation_id", nullable = false)
    private SurveyInstallation surveyInstallation;

    @Column(name="store_setting", nullable = false)
    private StoreSetting storeSetting;

    @Column(nullable = false)
    private String value;
}
