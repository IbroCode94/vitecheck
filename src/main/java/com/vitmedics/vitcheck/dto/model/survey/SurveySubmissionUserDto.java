package com.vitmedics.vitcheck.dto.model.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveySubmissionUserDto {

    @NotEmpty
    @Email
    private String email;
    private String firstName;
    private String lastName;

    @NotNull
    private boolean optedIn;
}
