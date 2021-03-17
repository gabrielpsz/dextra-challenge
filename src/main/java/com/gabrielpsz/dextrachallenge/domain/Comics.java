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
public class Comics{
    private int available;
    private String collectionURI;
    private List<Item> items;
    private int returned;
    private String title;

    public Comics(String title) {
        this.title = title;
    }
}
