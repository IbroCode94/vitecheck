package com.vitmedics.vitcheck.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.Date;
import java.util.HashMap;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIResponseError {

    private int status;
    private HashMap<String, String> errors;

    private Date timestamp;
    private String message;
    private String details;


    public APIResponseError(int status, String message) {
        this.status = status;
        this.message = message;
        this.errors = new HashMap<>();
    }
}
