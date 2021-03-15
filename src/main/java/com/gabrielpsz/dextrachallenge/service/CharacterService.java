package com.gabrielpsz.dextrachallenge.service;

import com.gabrielpsz.dextrachallenge.domain.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CharacterService {

    private static String BASE_URL = "https://gateway.marvel.com:443/v1/public/characters";
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

    public List<MarvelCharacter> getAllCharacters() {
        String timestamp = Long.toString(System.currentTimeMillis());
        String md5Hash = DigestUtils.md5Hex(timestamp.concat(PRIVATE_API_KEY).concat(PUBLIC_API_KEY));
        ResponseEntity<MarvelResponse> forEntity = restTemplate.getForEntity(BASE_URL.concat("?apikey=" + PUBLIC_API_KEY).concat("&ts="+timestamp).concat("&hash="+md5Hash), MarvelResponse.class);
        return forEntity.getBody().getData().getResults();
    }

    public MarvelCharacter getCharacterById(String id) {
        return null;
    }

    public List<Comics> getCharacterComics(String id) {
        return Collections.emptyList();
    }

    public List<Events> getCharacterEvents(String id) {
        return Collections.emptyList();
    }

    public List<Series> getCharacterSeries(String id) {
        return Collections.emptyList();
    }

    public List<Stories> getCharacterStories(String id) {
        return Collections.emptyList();
    }
}
