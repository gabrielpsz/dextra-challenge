package com.gabrielpsz.dextrachallenge.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielpsz.dextrachallenge.constants.MarvelEndpoints;
import com.gabrielpsz.dextrachallenge.domain.*;
import com.gabrielpsz.dextrachallenge.exceptions.EmptyRequestContent;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CharacterService {

    @Value("${marvel.apiKey.public}")
    private String PUBLIC_API_KEY;
    @Value("${marvel.apiKey.private}")
    private String PRIVATE_API_KEY;
    private ObjectMapper mapper;
    private final RestTemplate restTemplate;

    public CharacterService() {
        this.restTemplate = new RestTemplate();
        this.mapper = new ObjectMapper();
    }

    public List<MarvelCharacter> getAllCharacters(Map<String,String> params) throws HttpStatusCodeException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(MarvelEndpoints.BASE_URL, MarvelEndpoints.CHARACTERS, timestamp, md5Hash, params);
        try {
            ResponseEntity<MarvelResponse> forEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = forEntity.getBody().getData().getResults();
            List<MarvelCharacter> list = mapper.convertValue(results, new TypeReference<>() { });
            return list;
        } catch(HttpStatusCodeException e) {
            throw e;
        }
    }

    public MarvelCharacter getCharacterById(String id, Map<String,String> params) throws HttpStatusCodeException, NumberFormatException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(MarvelEndpoints.BASE_URL, MarvelEndpoints.CHARACTER_BY_ID.replaceAll("\\{id}", id), timestamp, md5Hash, params);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            return getMarvelCharacterFromMarvelResponse(responseEntity);
        } catch (HttpStatusCodeException e) {
            throw e;
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public List<Comics> getCharacterComics(String id, Map<String,String> params) throws EmptyRequestContent, HttpStatusCodeException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(MarvelEndpoints.BASE_URL, MarvelEndpoints.COMICS_BY_CHARACTER_ID.replaceAll("\\{id}", id), timestamp, md5Hash, params);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            List<Comics> list = mapper.convertValue(results, new TypeReference<>() { });
            if (list.size() == 0) {
                throw new EmptyRequestContent(HttpStatus.NOT_FOUND.value(), "There is no comics for this character.");
            }
            return list;
        } catch (HttpStatusCodeException e) {
            throw e;
        }
    }

    public List<Events> getCharacterEvents(String id, Map<String,String> params) throws EmptyRequestContent, HttpStatusCodeException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(MarvelEndpoints.BASE_URL, MarvelEndpoints.EVENTS_BY_CHARACTER_ID.replaceAll("\\{id}", id), timestamp, md5Hash, params);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            List<Events> list = mapper.convertValue(results, new TypeReference<>() { });
            if (list.size() == 0) {
                throw new EmptyRequestContent(HttpStatus.NOT_FOUND.value(), "There is no events for this character.");
            }
            return list;
        } catch (HttpStatusCodeException e) {
            throw e;
        }
    }

    public List<Series> getCharacterSeries(String id, Map<String,String> params) throws EmptyRequestContent, HttpStatusCodeException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(MarvelEndpoints.BASE_URL, MarvelEndpoints.SERIES_BY_CHARACTER_ID.replaceAll("\\{id}", id), timestamp, md5Hash, params);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            List<Series> list = mapper.convertValue(results, new TypeReference<>() { });
            if (list.size() == 0) {
                throw new EmptyRequestContent(HttpStatus.NOT_FOUND.value(), "There is no series for this character.");
            }
            return list;
        } catch (HttpStatusCodeException e) {
            throw e;
        }
    }

    public List<Stories> getCharacterStories(String id, Map<String,String> params) throws EmptyRequestContent, HttpStatusCodeException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(MarvelEndpoints.BASE_URL, MarvelEndpoints.STORIES_BY_CHARACTER_ID.replaceAll("\\{id}", id), timestamp, md5Hash, params);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            List<Stories> list = mapper.convertValue(results, new TypeReference<>() { });
            if (list.size() == 0) {
                throw new EmptyRequestContent(HttpStatus.NOT_FOUND.value(), "There is no stories for this character.");
            }
            return list;
        } catch (HttpStatusCodeException e) {
            throw e;
        }
    }

    public String getUrl(String baseUrl, String endpoint, String timestamp, String md5Hash, Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(baseUrl);
        urlBuilder.append(endpoint);
        urlBuilder.append("?apikey=" + PUBLIC_API_KEY);
        urlBuilder.append("&ts="+timestamp);
        urlBuilder.append("&hash="+md5Hash);
        if (!MapUtils.isEmpty(params)) {
            for(String key:params.keySet()){
                urlBuilder.append("&"+key+"="+params.get(key));
            }
        }
        return urlBuilder.toString();
    }

    public String getMarvelMd5Hash(String timestamp) {
        return DigestUtils.md5Hex(timestamp.concat(PRIVATE_API_KEY).concat(PUBLIC_API_KEY));
    }

    public MarvelCharacter getMarvelCharacterFromMarvelResponse(ResponseEntity<MarvelResponse> responseEntity) {
        List<Object> results = responseEntity.getBody().getData().getResults();
        List<MarvelCharacter> marvelCharacterList = mapper.convertValue(results, new TypeReference<>() { });
        return marvelCharacterList.get(0);
    }

}
