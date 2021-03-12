package com.gabrielpsz.dextrachallenge.domain;

import lombok.Data;

import java.util.List;

@Data
public class ResponseData {
    public int offset;
    public int limit;
    public int total;
    public int count;
    public List<MarvelCharacter> results;
}
