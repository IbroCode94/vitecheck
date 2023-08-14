package com.vitmedics.vitcheck.controller.v1.api;

import com.vitmedics.vitcheck.dto.APIResponse;
import com.vitmedics.vitcheck.dto.APIResponseError;
import com.vitmedics.vitcheck.dto.model.survey.MedicationDto;
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
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/medications")
@Validated
public interface MedicationsController {

    @Operation(description = "Retrieve a list of medications that match provided filter text, limiting results to a provided value or 10 by default", tags= { })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of medications",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MedicationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filter or limit parameters provided",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponseError.class)))
    })
    @GetMapping
    APIResponse getMedications(@Valid @RequestParam final Optional<String> filterText, @Valid @RequestParam(defaultValue = "10") final int limit, @RequestParam(required = false) List<Integer> nutrientIdFilter);
}
