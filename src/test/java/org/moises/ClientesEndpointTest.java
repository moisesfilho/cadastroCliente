package org.moises;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ClientesEndpointTest {

    @Test
    public void operacoesComClientes() {
        //Listar todos os 4 clientes já criados na inicialização do banco de dados:
        given()
                .when().get("/clientes")
                .then()
                .statusCode(200)
                .body(
                        containsString("Moisés Carneiro"),
                        containsString("Pedro Silva"),
                        containsString("Leonardo Silva"),
                        containsString("Carla Silva")); 

        //Deletando Cliente Moisés:
        given()
                .when().delete("/clientes/1")
                .then()
                .statusCode(204);

        //Listando todos os clientes, sem conter o que foi deletado:
        given()
                .when().get("/clientes")
                .then()
                .statusCode(200)
                .body(
                        not(containsString("Moisés Carneiro")),
                        containsString("Pedro Silva"),
                        containsString("Leonardo Silva"),
                        containsString("Carla Silva"));

        //Criando a cliente Juliana:
        given()
                .when()
                .body("{\"nome\" : \"Juliana Alves\", \"cpf\" : \"20945225040\"}")
                .contentType("application/json")
                .post("/clientes")
                .then()
                .statusCode(201);

        //Buscando novo cliente na listagem por CPF
        given()
                .when().get("/clientes")
                .then()
                .statusCode(200)
                .body(
                        containsString("20945225040"));

        //Listando todos, com o novo cliente:
        given()
                .when().get("/clientes")
                .then()
                .statusCode(200)
                .body(
                        not(containsString("Moisés Carneiro")),
                        containsString("Juliana Alves"),
                        containsString("Pedro Silva"),
                        containsString("Leonardo Silva"),
                        containsString("Carla Silva"));
    }

}
