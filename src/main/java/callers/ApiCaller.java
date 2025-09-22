package callers;


import DTO.AccountDTO;
import DTO.RentRequestDTO;
import DTO.TokenDTO;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requestspecification.BibliotecaRequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiCaller {

    private final RequestSpecification requestSpec;


    public ApiCaller() {
        requestSpec = new BibliotecaRequestSpecification().getRequestSpecification();
    }


    public Response criarUsuario(AccountDTO account) {

        return given()
                .spec(requestSpec)
                .when()
                .body(account)
                .post("/Account/v1/User")
                .then()
                .extract()
                .response();
    }

    public Response exibirDadosUsuario(String token, String id) {
        return given().header("Authorization", token)
                .spec(requestSpec)
                .when()
                .pathParam("ID",id)
                .get("/Account/v1/User//{ID}")
                .then()
                .extract().response();

    }

    public Response gerarToken(AccountDTO account) {

        return given().auth().basic(account.getUserName(), account.getPassword())
                .spec(requestSpec)
                .when()
                .body(account)
                .post("/Account/v1/GenerateToken").prettyPeek()
                .then()
                .extract()
                .response();
                 }


    public Boolean validarAutorizacao(AccountDTO account) {
        return given()
                .spec(requestSpec)
                .when()
                .body(account)
                .post("/Account/v1/Authorized")
                .then()
                .extract()
                .response().as(Boolean.class);
    }


    public Response listarLivros() {
        return given()
                .spec(requestSpec)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .extract()
                .response();
    }

    public Response alugarLivros(TokenDTO token,RentRequestDTO rentRequest) {

        return given()
                .header("Authorization", token.getToken())
                .spec(requestSpec)
                .when()
                .body(rentRequest)
                .post("/BookStore/v1/Books")  .prettyPeek()
                .then()
                .extract().response();

    }

}