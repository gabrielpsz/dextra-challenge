package com.gabrielpsz.dextrachallenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Series{
    private int id;
    private String title;
    private String description;
    private int available;
    private String collectionURI;
    private List<Item> items;
    private int returned;
}
