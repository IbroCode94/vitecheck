package com.vitmedics.vitcheck.dto.model.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class NutrientDto {
    private int id;
    private String name;
    private String summary;
    private String functionInBody;
    private String foodSources;
    private String otherInfo;
    private String importantFor;
    private String longTermEffects;
    private String refs;
}
