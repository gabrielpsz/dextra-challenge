package com.gabrielpsz.dextrachallenge.domain;

import lombok.Data;

import java.util.List;

@Data
public class Events{
    public int available;
    public String collectionURI;
    public List<Item> items;
    public int returned;
}
