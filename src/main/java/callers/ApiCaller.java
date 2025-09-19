package callers;


import DTO.AccountDTO;
import DTO.RentRequestDTO;
import DTO.TokenDTO;
import DTO.UserDTO;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import requestspecification.BibliotecaRequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiCaller {

    private final RequestSpecification requestSpec;


    public ApiCaller() {
        requestSpec = new BibliotecaRequestSpecification().getRequestSpecification();
    }


    public UserDTO criarUsuario(AccountDTO account, int expectedStatusCode) {

        return given()
                .spec(requestSpec)
                .when()
                .body(account)
                .post("/Account/v1/User")
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response()
                .jsonPath().getObject("", UserDTO.class);
    }

    public UserDTO exibirDadosUsuario(String token, String id) {
        //demoqa.com/Account/v1/User
        return given().header("Authorization", token)
                .spec(requestSpec)
                .when().log().all()
                .get("/Account/v1/User/" + id)
                .then().log().all()
                .extract()
                .body().jsonPath().getObject("", UserDTO.class);
    }

    public TokenDTO gerarToken(AccountDTO account, int expectedStatusCode) {

        return given().auth().basic(account.getUserName(), account.getPassword())
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
                .then()
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

    public JsonPath alugarLivros(TokenDTO token, RentRequestDTO rentRequest) {

        return given().header("Authorization", "Bearer " + token.getToken())
                .spec(requestSpec)
                .when().log().all()
                .body(rentRequest)
                .post("/BookStore/v1/Books")
                .then().log().all()
                .extract()
                .body().jsonPath();//.getList(".", BookDTO.class);
    }

}