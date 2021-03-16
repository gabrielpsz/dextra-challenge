package com.gabrielpsz.dextrachallenge.utils;

import lombok.Data;

import java.util.List;

@Data
public class Response<T> {
    private T data;
    private ApiError errors;
}
