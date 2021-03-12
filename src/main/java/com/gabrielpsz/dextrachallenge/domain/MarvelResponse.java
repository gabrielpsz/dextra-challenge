package com.gabrielpsz.dextrachallenge.domain;

import lombok.Data;

@Data
public class MarvelResponse {
    public int code;
    public String status;
    public String copyright;
    public String attributionText;
    public String attributionHTML;
    public String etag;
    public ResponseData data;
}
