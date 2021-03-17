package com.gabrielpsz.dextrachallenge.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielpsz.dextrachallenge.constants.MarvelEndpoints;
import com.gabrielpsz.dextrachallenge.domain.*;
import com.gabrielpsz.dextrachallenge.exceptions.EmptyRequestContent;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CharacterService {

    @Value("${marvel.apiKey.public}")
    private String PUBLIC_API_KEY;
    @Value("${marvel.apiKey.private}")
    private String PRIVATE_API_KEY;

    private final RestTemplate restTemplate;

    public CharacterService() {
        this.restTemplate = new RestTemplate();
    }

    public List<MarvelCharacter> getAllCharacters() throws HttpStatusCodeException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(MarvelEndpoints.BASE_URL, MarvelEndpoints.CHARACTERS, timestamp, md5Hash);
        try {
            ResponseEntity<MarvelResponse> forEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = forEntity.getBody().getData().getResults();
            return (List<MarvelCharacter>)(Object)results;
        } catch(HttpStatusCodeException e) {
            throw e;
        }
    }

    public MarvelCharacter getCharacterById(String id) throws HttpStatusCodeException, NumberFormatException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(MarvelEndpoints.BASE_URL, MarvelEndpoints.CHARACTER_BY_ID.replaceAll("\\{id}", id), timestamp, md5Hash);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            return getMarvelCharacterFromMarvelResponse(responseEntity);
        } catch (HttpStatusCodeException e) {
            throw e;
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public List<Comics> getCharacterComics(String id) throws EmptyRequestContent, HttpStatusCodeException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(MarvelEndpoints.BASE_URL, MarvelEndpoints.COMICS_BY_CHARACTER_ID.replaceAll("\\{id}", id), timestamp, md5Hash);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            ObjectMapper mapper = new ObjectMapper();
            List<Comics> list = mapper.convertValue(results, new TypeReference<>() { });
            if (list.size() == 0) {
                throw new EmptyRequestContent(HttpStatus.NOT_FOUND.value(), "There is no comics for this character.");
            }
            return list;
        } catch (HttpStatusCodeException e) {
            throw e;
        }
    }

    public List<Events> getCharacterEvents(String id) throws EmptyRequestContent, HttpStatusCodeException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(MarvelEndpoints.BASE_URL, MarvelEndpoints.EVENTS_BY_CHARACTER_ID.replaceAll("\\{id}", id), timestamp, md5Hash);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            ObjectMapper mapper = new ObjectMapper();
            List<Events> list = mapper.convertValue(results, new TypeReference<>() { });
            if (list.size() == 0) {
                throw new EmptyRequestContent(HttpStatus.NOT_FOUND.value(), "There is no events for this character.");
            }
            return list;
        } catch (HttpStatusCodeException e) {
            throw e;
        }
    }

    public List<Series> getCharacterSeries(String id) throws EmptyRequestContent, HttpStatusCodeException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(MarvelEndpoints.BASE_URL, MarvelEndpoints.SERIES_BY_CHARACTER_ID.replaceAll("\\{id}", id), timestamp, md5Hash);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            ObjectMapper mapper = new ObjectMapper();
            List<Series> list = mapper.convertValue(results, new TypeReference<>() { });
            if (list.size() == 0) {
                throw new EmptyRequestContent(HttpStatus.NOT_FOUND.value(), "There is no series for this character.");
            }
            return list;
        } catch (HttpStatusCodeException e) {
            throw e;
        }
    }

    public List<Stories> getCharacterStories(String id) throws EmptyRequestContent, HttpStatusCodeException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(MarvelEndpoints.BASE_URL, MarvelEndpoints.STORIES_BY_CHARACTER_ID.replaceAll("\\{id}", id), timestamp, md5Hash);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            ObjectMapper mapper = new ObjectMapper();
            List<Stories> list = mapper.convertValue(results, new TypeReference<>() { });
            if (list.size() == 0) {
                throw new EmptyRequestContent(HttpStatus.NOT_FOUND.value(), "There is no stories for this character.");
            }
            return list;
        } catch (HttpStatusCodeException e) {
            throw e;
        }
    }

    public String getUrl(String baseUrl, String endpoint, String timestamp, String md5Hash) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(baseUrl);
        urlBuilder.append(endpoint);
        urlBuilder.append("?apikey=" + PUBLIC_API_KEY);
        urlBuilder.append("&ts="+timestamp);
        urlBuilder.append("&hash="+md5Hash);
        return urlBuilder.toString();
    }

    public String getMarvelMd5Hash(String timestamp) {
        return DigestUtils.md5Hex(timestamp.concat(PRIVATE_API_KEY).concat(PUBLIC_API_KEY));
    }

    public MarvelCharacter getMarvelCharacterFromMarvelResponse(ResponseEntity<MarvelResponse> responseEntity) {
        List<Object> results = responseEntity.getBody().getData().getResults();
        ObjectMapper mapper = new ObjectMapper();
        List<MarvelCharacter> marvelCharacterList = mapper.convertValue(results, new TypeReference<>() { });
        return marvelCharacterList.get(0);
    }

}
