package com.microservices.productservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<R> {

    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime timestamp;
    private Integer status;
    private String key;
    private String message;
    private R data;
    private List<ValidationError> errors = new ArrayList<>();

    public static <R> BaseResponse<R> success(R data, String key) {
        return new BaseResponseBuilder<R>()
                .status(HttpStatus.OK.value())
                .key(key)
                .data(data)
                .build();
    }

    public static <R> BaseResponse<R> success(BaseResponse<?> data) {
        return new BaseResponseBuilder<R>()
                .status(HttpStatus.OK.value())
                .key("SUCCESS")
                .data((R) data)
                .build();
    }

    public static BaseResponse<Void> success(String key) {
        return new BaseResponseBuilder<Void>()
                .status(HttpStatus.OK.value())
                .key(key)
                .build();
    }

    public static BaseResponse<Void> failure(int httpStatusCode, String key, String message) {
        return new BaseResponseBuilder<Void>()
                .timestamp(OffsetDateTime.now())
                .status(httpStatusCode)
                .key(key)
                .message(message)
                .build();
    }

    public static <R> BaseResponse<R> failure(int httpStatusCode, String key, R data) {
        return new BaseResponseBuilder<R>()
                .timestamp(OffsetDateTime.now())
                .status(httpStatusCode)
                .key(key)
                .data(data)
                .build();
    }

    public static BaseResponse<Void> failure(int httpStatusCode, String key) {
        return new BaseResponseBuilder<Void>()
                .timestamp(OffsetDateTime.now())
                .status(httpStatusCode)
                .key(key)
                .build();
    }

    public void addValidationError(String field, String message) {
        if (Objects.isNull(errors)) errors = new ArrayList<>();
        errors.add(new ValidationError(field, message));
    }
}

@Getter
class ValidationError {

    private String field;
    private String message;

    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public ValidationError(String string) {
    }

}
