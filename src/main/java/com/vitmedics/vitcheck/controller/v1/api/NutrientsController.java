package com.vitmedics.vitcheck.controller.v1.api;

import com.vitmedics.vitcheck.dto.APIResponse;
import com.vitmedics.vitcheck.dto.APIResponseError;
import com.vitmedics.vitcheck.dto.model.survey.MedicationDto;
import com.vitmedics.vitcheck.dto.model.survey.NutrientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/api/v1/nutrients")
@Validated
public interface NutrientsController {

    @Operation(description = "Retrieve a list of nutrients", tags= { })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of nutrients",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = NutrientDto.class)) })
    })
    @GetMapping
    APIResponse getNutrients();
}
