package com.gabrielpsz.dextrachallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comics{
    public int available;
    public String collectionURI;
    public List<Item> items;
    public int returned;
}
