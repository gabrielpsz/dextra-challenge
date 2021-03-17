package com.gabrielpsz.dextrachallenge.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmptyRequestContent extends Exception {
    private int code;
    private String status;
}
