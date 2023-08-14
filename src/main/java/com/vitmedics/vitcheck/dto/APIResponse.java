package com.vitmedics.vitcheck.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vitmedics.vitcheck.utils.DateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIResponse<T> {
    private Status status;
    private T payload;
    private Object errors;
    private Object metadata;

    public static <T> APIResponse<T> badRequest() {
        APIResponse<T> response = new APIResponse<>();
        response.setStatus(Status.BAD_REQUEST);
        return response;
    }

    public static <T> APIResponse<T> ok() {
        APIResponse<T> response = new APIResponse<>();
        response.setStatus(Status.OK);
        return response;
    }

    public static <T> APIResponse<T> unauthorized() {
        APIResponse<T> response = new APIResponse<>();
        response.setStatus(Status.UNAUTHORIZED);
        return response;
    }

    public static <T> APIResponse<T> validationException() {
        APIResponse<T> response = new APIResponse<>();
        response.setStatus(Status.VALIDATION_EXCEPTION);
        return response;
    }

    public static <T> APIResponse<T> wrongCredentials() {
        APIResponse<T> response = new APIResponse<>();
        response.setStatus(Status.WRONG_CREDENTIALS);
        return response;
    }

    public static <T> APIResponse<T> accessDenied() {
        APIResponse<T> response = new APIResponse<>();
        response.setStatus(Status.ACCESS_DENIED);
        return response;
    }

    public static <T> APIResponse<T> exception() {
        APIResponse<T> response = new APIResponse<>();
        response.setStatus(Status.EXCEPTION);
        return response;
    }

    public static <T> APIResponse<T> notFound() {
        APIResponse<T> response = new APIResponse<>();
        response.setStatus(Status.NOT_FOUND);
        return response;
    }

    public static <T> APIResponse<T> duplicateEntity() {
        APIResponse<T> response = new APIResponse<>();
        response.setStatus(Status.DUPLICATE_ENTITY);
        return response;
    }

    public void addErrorMsgToResponse(String errorMsg, Exception ex) {
        APIResponseError error = new APIResponseError()
                .setDetails(errorMsg)
                .setMessage(ex.getMessage())
                .setTimestamp(DateUtils.today());
        setErrors(error);
    }

    public enum Status {
        OK, BAD_REQUEST, UNAUTHORIZED, VALIDATION_EXCEPTION, EXCEPTION, WRONG_CREDENTIALS, ACCESS_DENIED, NOT_FOUND, DUPLICATE_ENTITY
    }

    @Getter
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PageMetadata {
        private final int size;
        private final long totalElements;
        private final int totalPages;
        private final int number;

        public PageMetadata(int size, long totalElements, int totalPages, int number) {
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.number = number;
        }
    }
}
