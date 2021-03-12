package com.gabrielpsz.dextrachallenge.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MarvelCharacter {
    public int id;
    public String name;
    public String description;
    public Date modified;
    public Thumbnail thumbnail;
    public String resourceURI;
    public Comics comics;
    public Series series;
    public Stories stories;
    public Events events;
    public List<Url> urls;
}



