package com.vitmedics.vitcheck.dto.mapper;

import com.vitmedics.vitcheck.dto.model.survey.NutrientDto;
import com.vitmedics.vitcheck.model.entities.survey.Nutrient;

public class NutrientMapper {

    public static NutrientDto toNutrientDto(Nutrient nutrient) {
        return new NutrientDto()
                .setId(nutrient.getId())
                .setName(nutrient.getName())
                .setFoodSources(nutrient.getFoodSources())
                .setFunctionInBody(nutrient.getFunctionInBody())
                .setImportantFor(nutrient.getImportantFor())
                .setRefs(nutrient.getRefs())
                .setSummary(nutrient.getSummary())
                .setOtherInfo(nutrient.getOtherInfo())
                .setLongTermEffects(nutrient.getLongTermEffects());
    }
}
