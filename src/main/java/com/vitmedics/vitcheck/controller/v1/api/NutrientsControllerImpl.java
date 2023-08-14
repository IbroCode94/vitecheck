package com.vitmedics.vitcheck.controller.v1.api;

import com.vitmedics.vitcheck.dto.APIResponse;
import com.vitmedics.vitcheck.service.NutrientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NutrientsControllerImpl implements NutrientsController {

    @Autowired
    private NutrientService nutrientService;

    @GetMapping
    public APIResponse getNutrients() {

        return APIResponse
                .ok()
                .setPayload(nutrientService.getNutrients());
    }
}
