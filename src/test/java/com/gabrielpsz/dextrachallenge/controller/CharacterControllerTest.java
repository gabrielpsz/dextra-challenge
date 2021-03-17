package com.gabrielpsz.dextrachallenge.controller;

import com.gabrielpsz.dextrachallenge.domain.MarvelCharacter;
import com.gabrielpsz.dextrachallenge.exceptions.ApiErrorException;
import com.gabrielpsz.dextrachallenge.service.CharacterService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientResponseException;


@WebMvcTest(CharacterController.class)
public class CharacterControllerTest {

    @MockBean
    private CharacterService characterService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void shouldReturnSuccess_whenCallGetCharacterById() {
        Mockito.when(characterService.getCharacterById("1")).thenReturn(
                new MarvelCharacter(1));

        RestAssuredMockMvc
                .given()
                .accept(ContentType.JSON)
                .when()
                .get("/v1/public/characters/{id}", "1")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1));
    }

    @Test
    public void shouldReturnError_whenCallGetCharacterByIdWithIdThatDoesntExists() {
        String errorObject = "{\"error\":404, \"status\":\"We couldn't find that character\"}";
        Mockito.when(characterService.getCharacterById("-1")).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "We couldn't find that character", errorObject.getBytes(), null));

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/v1/public/characters/{id}", "-1")
                .then()
                .statusCode(404)
                .body("code", Matchers.equalTo(404))
                .body("message", Matchers.equalTo("We couldn't find that character"));

    }

}
