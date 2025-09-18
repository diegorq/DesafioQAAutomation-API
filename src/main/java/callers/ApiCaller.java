package callers;


import DTO.*;
import io.restassured.specification.RequestSpecification;
import requestspecification.BibliotecaRequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiCaller {

    private RequestSpecification requestSpec;


    public ApiCaller() {
        requestSpec = new BibliotecaRequestSpecification().getRequestSpecification();
    }


    public UserDTO criarUsuario(AccountDTO account, int expectedStatusCode) {

        return given()
                .spec(requestSpec)
                .when().log().all()
                .body(account)
                .post("/Account/v1/User")
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract()
                .response()
                .jsonPath().getObject("", UserDTO.class);
    }

    public TokenDTO gerarToken(AccountDTO account, int expectedStatusCode) {

        return given()
                .spec(requestSpec)
                .when()
                .body(account)
                .post("/Account/v1/GenerateToken")
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response()
                .jsonPath().getObject("", TokenDTO.class);
    }


    public Boolean validarAutorizacao(AccountDTO account, int expectedStatusCode) {
        return given()
                .spec(requestSpec)
                .when()
                .body(account)
                .post("/Account/v1/Authorized")
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract()
                .response().as(Boolean.class);
    }


    public List<String> listarLivros() {
        return given()
                .spec(requestSpec)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .statusCode(200)
                .extract()
                .response().jsonPath().getList("books.isbn");
    }

    public List<BookDTO> alugarLivros(RentRequestDTO rentRequest) {
        return given()
                .spec(requestSpec)
                .when()
                .body(rentRequest)
                .post("/BookStore/v1/Books")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getList(".", BookDTO.class);
    }

}