package br.com.lex4crypto.monolito.controllers;



import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;


import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarteiraControllerTest {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        RestAssured.port = randomPort;
    }

    @Test
    void whenfindAllAuthorizedUser() {
        given()
                .when()
                .auth()
                .basic("joao123","1212" )
                .get("/carteiras")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void whenfindAllUnauthorizedUser() {
        given()
                .when()
                .auth()
                .basic("joao123","1212" )
                .get("/carteiras")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

}
