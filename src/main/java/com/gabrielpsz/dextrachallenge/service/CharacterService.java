package com.gabrielpsz.dextrachallenge.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielpsz.dextrachallenge.constants.MarvelEndpoints;
import com.gabrielpsz.dextrachallenge.domain.*;
import com.gabrielpsz.dextrachallenge.exceptions.ApiErrorException;
import com.gabrielpsz.dextrachallenge.utils.ApiError;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class CharacterService {

    private static String BASE_URL = "https://gateway.marvel.com:443";

    @Value("${marvel.apiKey.public}")
    private String PUBLIC_API_KEY;
    @Value("${marvel.apiKey.private}")
    private String PRIVATE_API_KEY;

    private final WebClient webClient;
    private final RestTemplate restTemplate;

    public CharacterService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
        this.restTemplate = new RestTemplate();
    }

//    public Mono<MarvelCharacter> getCharacterByName(String name) {
//        return this.webClient.get().url("/{name}/details", name)
//                .retrieve().bodyToMono(MarvelCharacter.class);
//    }

    public List<MarvelCharacter> getAllCharacters() throws ApiErrorException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(BASE_URL, MarvelEndpoints.CHARACTERS, timestamp, md5Hash);
        try {
            ResponseEntity<MarvelResponse> forEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = forEntity.getBody().getData().getResults();
            return (List<MarvelCharacter>)(Object)results;
        } catch(HttpStatusCodeException e) {
            ApiError error = getApiError(e);
            throw new ApiErrorException(error.getCode(), error.getStatus() == null ? error.getMessage() : error.getStatus());
        }
    }

    public MarvelCharacter getCharacterById(String id) throws ApiErrorException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(BASE_URL, MarvelEndpoints.CHARACTER_BY_ID.replaceAll("\\{id}", id), timestamp, md5Hash);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            ObjectMapper mapper = new ObjectMapper();
            List<MarvelCharacter> marvelCharacterList = mapper.convertValue(results, new TypeReference<>() { });
            return marvelCharacterList.get(0);
        } catch (HttpStatusCodeException e) {
            ApiError error = getApiError(e);
            throw new ApiErrorException(error.getCode(), error.getStatus() == null ? error.getMessage() : error.getStatus());
        }
    }

    public List<Comics> getCharacterComics(String id) throws ApiErrorException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(BASE_URL, MarvelEndpoints.COMICS_BY_CHARACTER_ID.replaceAll("\\{id}", id), timestamp, md5Hash);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            ObjectMapper mapper = new ObjectMapper();
            List<Comics> list = mapper.convertValue(results, new TypeReference<>() { });
            if (list.size() == 0) {
                throw new ApiErrorException("404", "There is no comics for this character.");
            }
            return list;
        } catch (HttpStatusCodeException e) {
            ApiError error = getApiError(e);
            throw new ApiErrorException(error.getCode(), error.getStatus() == null ? error.getMessage() : error.getStatus());
        } catch (ApiErrorException e) {
            throw e;
        }
    }

    public List<Events> getCharacterEvents(String id) throws ApiErrorException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(BASE_URL, MarvelEndpoints.EVENTS_BY_CHARACTER_ID.replaceAll("\\{id}", id), timestamp, md5Hash);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            ObjectMapper mapper = new ObjectMapper();
            List<Events> list = mapper.convertValue(results, new TypeReference<>() { });
            if (list.size() == 0) {
                throw new ApiErrorException("404", "There is no events for this character.");
            }
            return list;
        } catch (HttpStatusCodeException e) {
            ApiError error = getApiError(e);
            throw new ApiErrorException(error.getCode(), error.getStatus() == null ? error.getMessage() : error.getStatus());
        } catch (ApiErrorException e) {
            throw e;
        }
    }

    public List<Series> getCharacterSeries(String id) throws ApiErrorException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(BASE_URL, MarvelEndpoints.SERIES_BY_CHARACTER_ID.replaceAll("\\{id}", id), timestamp, md5Hash);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            ObjectMapper mapper = new ObjectMapper();
            List<Series> list = mapper.convertValue(results, new TypeReference<>() { });
            if (list.size() == 0) {
                throw new ApiErrorException("404", "There is no series for this character.");
            }
            return list;
        } catch (HttpStatusCodeException e) {
            ApiError error = getApiError(e);
            throw new ApiErrorException(error.getCode(), error.getStatus() == null ? error.getMessage() : error.getStatus());
        } catch (ApiErrorException e) {
            throw e;
        }
    }

    public List<Stories> getCharacterStories(String id) throws ApiErrorException {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = getMarvelMd5Hash(timestamp);
        String url = getUrl(BASE_URL, MarvelEndpoints.STORIES_BY_CHARACTER_ID.replaceAll("\\{id}", id), timestamp, md5Hash);
        try {
            ResponseEntity<MarvelResponse> responseEntity = restTemplate.getForEntity(url, MarvelResponse.class);
            List<Object> results = responseEntity.getBody().getData().getResults();
            ObjectMapper mapper = new ObjectMapper();
            List<Stories> list = mapper.convertValue(results, new TypeReference<>() { });
            if (list.size() == 0) {
                throw new ApiErrorException("404", "There is no stories for this character.");
            }
            return list;
        } catch (HttpStatusCodeException e) {
            ApiError error = getApiError(e);
            throw new ApiErrorException(error.getCode(), error.getStatus() == null ? error.getMessage() : error.getStatus());
        } catch (ApiErrorException e) {
            throw e;
        }
    }

    private String getUrl(String baseUrl, String endpoint, String timestamp, String md5Hash) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(baseUrl);
        urlBuilder.append(endpoint);
        urlBuilder.append("?apikey=" + PUBLIC_API_KEY);
        urlBuilder.append("&ts="+timestamp);
        urlBuilder.append("&hash="+md5Hash);
        return urlBuilder.toString();
    }

    private String getMarvelMd5Hash(String timestamp) {
        return DigestUtils.md5Hex(timestamp.concat(PRIVATE_API_KEY).concat(PUBLIC_API_KEY));
    }

    private ApiError getApiError(HttpStatusCodeException e) {
        String responseBodyAsString = e.getResponseBodyAsString();
        Gson gson = new Gson();
        return gson.fromJson(responseBodyAsString, ApiError.class);
    }

}
