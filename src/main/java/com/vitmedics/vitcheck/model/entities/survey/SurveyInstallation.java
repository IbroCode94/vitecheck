package com.vitmedics.vitcheck.model.entities.survey;

import com.vitmedics.vitcheck.model.entities.client.Client;
import com.vitmedics.vitcheck.model.enumeration.crmType.CRMType;
import com.vitmedics.vitcheck.model.enumeration.storeType.StoreType;
import com.vitmedics.vitcheck.model.enumeration.surveyInstallationSettingType.SurveyInstallationSettingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "survey_installation")
public class SurveyInstallation {
  @Id
  private UUID id = UUID.randomUUID();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_id", nullable = false)
  private Survey survey;

  @Column(length = 1000)
  private String description;

  @Column(name = "email_confirmation_enabled", nullable = false)
  private boolean emailConfirmationEnabled = false;

//  @Column(name = "results_view_type", nullable = false)
//  private ResultsViewType resultsViewType;

  @Column(name="installation_url", nullable = false)
  private String installationUrl;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyInstallation", cascade = CascadeType.ALL)
  private Set<SurveyInstallationSetting> surveyInstallationSettings;

  @Column(name="crm_type")
  private CRMType crmType;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyInstallation", cascade = CascadeType.ALL)
  private Set<SurveyInstallationCRMSetting> surveyInstallationCrmSettings;

  @Column(name="store_type", nullable = false)
  private StoreType storeType;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyInstallation", cascade = CascadeType.ALL)
  private Set<SurveyInstallationStoreSetting> surveyInstallationStoreSettings;


  public Optional<SurveyInstallationSetting> getSurveyInstallationSetting(SurveyInstallationSettingType setting) {
    return surveyInstallationSettings.stream().filter(s -> s.getSetting() == setting).findFirst();
  }

  public Map<SurveyInstallationSettingType, Object> getSurveyInstallationSettingsAsTypedMap() {
    return surveyInstallationSettings.stream().collect(Collectors.toMap(SurveyInstallationSetting::getSetting, SurveyInstallationSetting::getCastSettingValue));
  }

}
