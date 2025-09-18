package stepDefinitions;

import DTO.*;
import callers.ApiCaller;
import core.Utils;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class AccountSteps {

	private Response response;
	private static String jsonString;
	String values;
    ApiCaller api = new ApiCaller();
	Utils util = new Utils();
    UserDTO usuario = new UserDTO();
    AccountDTO conta =new AccountDTO("teste16","Aq1234er@");//new AccountDTO();
    TokenDTO token = new TokenDTO();
    List<String> livros;




    @Dado("que eu cadastre um novo usuário")
    public void que_eu_cadastre_um_novo_usuário() {
        conta = new AccountDTO(util.gerarNomeUsuario(),util.gerarSenha());
        usuario= api.criarUsuario(conta,201);
    }
    @Dado("gere um token de acesso")
    public void gere_um_token_de_acesso() {
        token= api.gerarToken(conta,200);
    }
    @Dado("esteja autorizado")
    public void esteja_autorizado() {
       assertTrue("Falha - Usuário não autorizado.", api.validarAutorizacao(conta,200));

    }
    @Quando("eu listar os livros disponíveis")
    public void eu_listar_os_livros_disponíveis() {
        livros = api.listarLivros();
    }
    @Quando("realizar o aluguel de {int} livros")
    public void realizar_o_aluguel_de_livros(int qtd) {
        RentRequestDTO rentRequest = new RentRequestDTO();
        rentRequest.setUserID(usuario.getUserID());
        List<Object> livrosParaAlugar = new ArrayList<>();
        livrosParaAlugar=  util.selecionarNItensDeUmaLista((livros),2);

        List<String> strings = livrosParaAlugar.stream()
                .map(object -> Objects.toString(object, null))
                .toList();


        rentRequest.setCollectionOfIsbns(strings);
        api.alugarLivros(rentRequest);
    }
    @Então("os livros alugados são listados nos dados do meu usuário")
    public void os_livros_alugados_são_listados_nos_dados_do_meu_usuário() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
