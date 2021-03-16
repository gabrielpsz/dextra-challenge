package com.gabrielpsz.dextrachallenge.controller;

import com.gabrielpsz.dextrachallenge.domain.*;
import com.gabrielpsz.dextrachallenge.exceptions.ApiErrorException;
import com.gabrielpsz.dextrachallenge.utils.ApiError;
import com.gabrielpsz.dextrachallenge.utils.Response;
import com.gabrielpsz.dextrachallenge.service.CharacterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/public/characters")
public class CharacterController {

    private CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public ResponseEntity<Response<List<MarvelCharacter>>> getAllCharacters() {
        log.info("Request to get all characters");
        Response<List<MarvelCharacter>> response = new Response<>();
        List<MarvelCharacter> allCharacters = null;
        try {
            allCharacters = characterService.getAllCharacters();
        } catch(ApiErrorException requestError) {
            setError(response, requestError);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setData(allCharacters);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<MarvelCharacter>> getCharacterById(@PathVariable String id) {
        log.info("Request to get character with id {}", id);
        Response<MarvelCharacter> response = new Response<>();
        MarvelCharacter character = null;
        try {
            character = characterService.getCharacterById(id);
        } catch (ApiErrorException requestError) {
            setError(response, requestError);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setData(character);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}/comics")
    public ResponseEntity<Response<List<Comics>>> getCharacterComics(@PathVariable String id) {
        log.info("Request to get comics list from character with id {}", id);
        Response<List<Comics>> response = new Response<>();
        List<Comics> characterComics = null;
        try {
            characterComics = characterService.getCharacterComics(id);
        } catch (ApiErrorException requestError) {
            setError(response, requestError);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setData(characterComics);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<Response<List<Events>>> getCharacterEvents(@PathVariable String id) {
        log.info("Request to get events list from character with id {}", id);
        Response<List<Events>> response = new Response<>();
        List<Events> characterEvents = null;
        try {
            characterEvents = characterService.getCharacterEvents(id);
        } catch (ApiErrorException requestError) {
            setError(response, requestError);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setData(characterEvents);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}/series")
    public ResponseEntity<Response<List<Series>>> getCharacterSeries(@PathVariable String id) {
        log.info("Request to get series list from character with id {}", id);
        Response<List<Series>> response = new Response<>();
        List<Series> characterSeries = null;
        try {
            characterSeries = characterService.getCharacterSeries(id);
        } catch (ApiErrorException requestError) {
            setError(response, requestError);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setData(characterSeries);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}/stories")
    public ResponseEntity<Response<List<Stories>>> getCharacterStories(@PathVariable String id) {
        log.info("Request to get stories list from character with id {}", id);
        Response<List<Stories>> response = new Response<>();
        List<Stories> characterStories = null;
        try {
            characterStories = characterService.getCharacterStories(id);
        } catch (ApiErrorException requestError) {
            setError(response, requestError);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setData(characterStories);
        return ResponseEntity.ok().body(response);
    }

    private <T> void setError(Response<T> response, ApiErrorException requestError) {
        ApiError error = new ApiError(requestError.getCode(), requestError.getStatus());
        response.setErrors(error);
    }

}
