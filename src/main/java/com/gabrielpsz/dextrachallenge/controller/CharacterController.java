package com.gabrielpsz.dextrachallenge.controller;

import com.gabrielpsz.dextrachallenge.domain.*;
import com.gabrielpsz.dextrachallenge.exceptions.ApiErrorException;
import com.gabrielpsz.dextrachallenge.service.CharacterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

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
    public ResponseEntity<List<MarvelCharacter>> getAllCharacters()  {
        log.info("Request to get all characters");
        List<MarvelCharacter> allCharacters = characterService.getAllCharacters();
        return ResponseEntity.ok().body(allCharacters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarvelCharacter> getCharacterById(@PathVariable String id) throws HttpStatusCodeException {
        log.info("Request to get character with id {}", id);
        MarvelCharacter character = characterService.getCharacterById(id);
        return ResponseEntity.ok().body(character);
    }

    @GetMapping("/{id}/comics")
    public ResponseEntity<List<Comics>> getCharacterComics(@PathVariable String id) throws ApiErrorException {
        log.info("Request to get comics list from character with id {}", id);
        List<Comics> characterComics = characterService.getCharacterComics(id);
        return ResponseEntity.ok().body(characterComics);
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<List<Events>> getCharacterEvents(@PathVariable String id) throws ApiErrorException {
        log.info("Request to get events list from character with id {}", id);
        List<Events> characterEvents = characterService.getCharacterEvents(id);
        return ResponseEntity.ok().body(characterEvents);
    }

    @GetMapping("/{id}/series")
    public ResponseEntity<List<Series>> getCharacterSeries(@PathVariable String id) throws ApiErrorException {
        log.info("Request to get series list from character with id {}", id);
        List<Series> characterSeries = characterService.getCharacterSeries(id);
        return ResponseEntity.ok().body(characterSeries);
    }

    @GetMapping("/{id}/stories")
    public ResponseEntity<List<Stories>> getCharacterStories(@PathVariable String id) throws ApiErrorException {
        log.info("Request to get stories list from character with id {}", id);
        List<Stories> characterStories = characterService.getCharacterStories(id);
        return ResponseEntity.ok().body(characterStories);
    }

}
