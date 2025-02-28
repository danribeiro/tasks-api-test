package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void test(){
        RestAssured.given()
            .log().all()
        .when()
            .get("/todo")
        .then()
            .statusCode(200);
    }

    @Test
    public void deveAdicionarTarefaComSucesso(){
        RestAssured.given()
            .body("{\"task\":\"teste\",\"dueDate\":\"2025-10-11\"}")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .statusCode(201);
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida(){
        RestAssured.given()
            .body("{\"task\":\"teste\",\"dueDate\":\"2025-01-11\"}")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .statusCode(400)
            .body("message",CoreMatchers.is("Due date must not be in past"));
    }
}

