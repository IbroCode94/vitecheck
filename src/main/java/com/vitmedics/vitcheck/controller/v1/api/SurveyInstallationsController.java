package com.vitmedics.vitcheck.controller.v1.api;

import com.vitmedics.vitcheck.dto.APIResponse;
import com.vitmedics.vitcheck.dto.APIResponseError;
import com.vitmedics.vitcheck.dto.model.survey.SurveyInstallationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@RequestMapping("/api/v1/survey-installations")
public interface SurveyInstallationsController {

    @Operation(description = "Retrieve a list of survey installations", tags= { "Admin" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of survey installations",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SurveyInstallationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filter or limit parameters provided",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponseError.class)))
    })
    @GetMapping
    APIResponse getSurveyInstallations();

    @Operation(description = "Retrieve a survey installation by id", tags= { })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A survey installation matching the provided identifier",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SurveyInstallationDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Survey installation with the requested id not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponseError.class))),
            @ApiResponse(responseCode = "400", description = "Invalid id parameter provided",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponseError.class)))
    })
    @GetMapping("{id}")
    APIResponse getSurveyInstallationById(@Valid @PathVariable final UUID id);
}
