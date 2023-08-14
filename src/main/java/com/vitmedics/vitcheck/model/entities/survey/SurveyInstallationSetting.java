package com.vitmedics.vitcheck.model.entities.survey;

import com.vitmedics.vitcheck.model.enumeration.resultsViewType.ResultsViewType;
import com.vitmedics.vitcheck.model.enumeration.surveyInstallationSettingType.SurveyInstallationSettingType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Arrays;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "survey_installation_setting", uniqueConstraints = { @UniqueConstraint(columnNames = {"survey_installation_id", "setting"})})
public class SurveyInstallationSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "survey_installation_id", nullable = false)
    private SurveyInstallation surveyInstallation;

    @Column(name="setting", nullable = false)
    private SurveyInstallationSettingType setting;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private String valueType = String.class.getName();

    public Object getCastSettingValue() {
        try {
            switch(valueType) {
                case "java.lang.Integer":
                    return Integer.parseInt(value);
                case "java.lang.Long":
                    return Long.parseLong(value);
                case "java.lang.Boolean":
                    return Boolean.parseBoolean(value);
                case "com.vitmedics.vitcheck.model.enumeration.resultsViewType.ResultsViewType":
                    return ResultsViewType.valueOf(value);
                default:
                    return value;
            }
        } catch(Exception ex) {
            return value;
        }
    }
}
