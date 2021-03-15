package com.gabrielpsz.dextrachallenge.controller;

import com.gabrielpsz.dextrachallenge.domain.*;
import com.gabrielpsz.dextrachallenge.service.CharacterService;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<List<MarvelCharacter>> getAllCharacters() {
        log.info("Request to get all characters");
        return ResponseEntity.ok().body(characterService.getAllCharacters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarvelCharacter> getCharacterById(@PathVariable String id) {
        log.info("Request to get character with id {}", id);
        return ResponseEntity.ok().body(characterService.getCharacterById(id));
    }

    @GetMapping("/{id}/comics")
    public ResponseEntity<List<Comics>> getCharacterComics(@PathVariable String id) {
        log.info("Request to get comics list from character with id {}", id);
        return ResponseEntity.ok().body(characterService.getCharacterComics(id));
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<List<Events>> getCharacterEvents(@PathVariable String id) {
        log.info("Request to get events list from character with id {}", id);
        return ResponseEntity.ok().body(characterService.getCharacterEvents(id));
    }

    @GetMapping("/{id}/series")
    public ResponseEntity<List<Series>> getCharacterSeries(@PathVariable String id) {
        log.info("Request to get series list from character with id {}", id);
        return ResponseEntity.ok().body(characterService.getCharacterSeries(id));
    }

    @GetMapping("/{id}/stories")
    public ResponseEntity<List<Stories>> getCharacterStories(@PathVariable String id) {
        log.info("Request to get stories list from character with id {}", id);
        return ResponseEntity.ok().body(characterService.getCharacterStories(id));
    }

}
