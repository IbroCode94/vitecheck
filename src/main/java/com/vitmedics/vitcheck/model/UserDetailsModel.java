package com.vitmedics.vitcheck.model;

import com.vitmedics.vitcheck.dto.model.survey.SurveySubmissionUserDto;
import com.vitmedics.vitcheck.model.entities.survey.SurveySubmissionUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class UserDetailsModel {

    private String email;
    private String firstName;
    private String lastName;
    private boolean optedIn;

    public UserDetailsModel(String email, String firstName, String lastName, boolean optedIn) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.optedIn = optedIn;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public static UserDetailsModel FromSurveySubmissionUserDto(SurveySubmissionUserDto dto) {
        return new UserDetailsModel(dto.getEmail(), dto.getFirstName(), dto.getLastName(), dto.isOptedIn());
    }

    public static UserDetailsModel FromSurveySubmissionUser(SurveySubmissionUser entity) {
        return new UserDetailsModel(entity.getEmail(), entity.getFirstName(), entity.getLastName(), entity.isOptedIn());
    }
}
