package com.vitmedics.vitcheck.service;

import com.vitmedics.vitcheck.dto.model.survey.NutrientDto;

import java.util.List;

public interface NutrientService {
    List<NutrientDto> getNutrients();
}

