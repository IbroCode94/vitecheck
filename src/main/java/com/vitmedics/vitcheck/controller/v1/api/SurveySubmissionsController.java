package com.vitmedics.vitcheck.controller.v1.api;

import com.vitmedics.vitcheck.controller.v1.request.SetSurveySubmissionUserRequest;
import com.vitmedics.vitcheck.controller.v1.request.SurveySubmissionRequest;
import com.vitmedics.vitcheck.dto.APIResponse;
import com.vitmedics.vitcheck.dto.APIResponseError;
import com.vitmedics.vitcheck.dto.model.survey.SurveyInstallationDto;
import com.vitmedics.vitcheck.dto.model.survey.SurveySubmissionResultsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.UUID;

@Validated
@RequestMapping("/api/v1/survey-submissions")
public interface SurveySubmissionsController {

    @Operation(description = "Submit a new survey submission for a given installation", tags= { })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "An identifier for the created submission is returned in the response",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Survey submission failed validation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponseError.class)))
    })
    @PostMapping
    APIResponse createSurveySubmission(@Valid @RequestBody @NotNull final SurveySubmissionRequest request);


    @Operation(description = "Get the results associated with a survey submission", tags= { })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A response containing the analysed results for a given survey submission matching the provided id and installation",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SurveySubmissionResultsDto.class)) }),
            @ApiResponse(responseCode = "404", description = "No submission found with the provided identifier & installation identifier combination",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponseError.class))),
            @ApiResponse(responseCode = "400", description = "Invalid identifiers provided",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponseError.class)))
    })
    @GetMapping("{surveySubmissionId}/results")
    APIResponse getSurveySubmissionResults(
            @Valid @PathVariable @NotNull final UUID surveySubmissionId,
            @Valid @RequestParam(required = true) @NotNull final UUID surveyInstallationId) throws IOException;

    @Operation(description = "Set the user details against a given survey submission", tags= { })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details set successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }),
            @ApiResponse(responseCode = "404", description = "No submission found with the provided identifier",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponseError.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body.  Check error response for details.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponseError.class)))
    })
    @PostMapping("{surveySubmissionId}/user")
    APIResponse setSurveySubmissionUser(@Valid @PathVariable @NotNull final UUID surveySubmissionId, @Valid @RequestBody final SetSurveySubmissionUserRequest request) throws IOException;
}
