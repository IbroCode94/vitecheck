package com.vitmedics.vitcheck.model.entities.survey;

import com.vitmedics.vitcheck.model.enumeration.crmSetting.CRMSetting;
import com.vitmedics.vitcheck.model.enumeration.storeSetting.StoreSetting;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "survey_installation_crm_setting", uniqueConstraints = { @UniqueConstraint(columnNames = {"survey_installation_id", "crm_setting"})})
public class SurveyInstallationCRMSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "survey_installation_id", nullable = false)
    private SurveyInstallation surveyInstallation;

    @Column(name="crm_setting", nullable = false)
    private CRMSetting crmSetting;

    @Column(nullable = false)
    private String value;
}
