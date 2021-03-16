package com.gabrielpsz.dextrachallenge.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private String code;
    private String status;
    private String message;

    public ApiError(String code, String status) {
        this.code = code;
        this.status = status;
    }
}
