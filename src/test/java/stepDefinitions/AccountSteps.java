package stepDefinitions;

import DTO.*;
import callers.ApiCaller;
import core.Utils;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import validations.Validacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

public class AccountSteps {
    ApiCaller api = new ApiCaller();
    Utils util = new Utils();
    UserDTO usuario = new UserDTO();
    AccountDTO conta = new AccountDTO();
    TokenDTO token = new TokenDTO();
    List<String> livros;
    Response r;
    Validacoes v;

    @Dado("que eu cadastre um novo usuário")
    public void que_eu_cadastre_um_novo_usuário() {
        conta = new AccountDTO(util.gerarNomeUsuario(), util.gerarSenha());
        r = api.criarUsuario(conta);
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(r.getStatusCode() == 201);
        usuario = r.jsonPath().getObject("", UserDTO.class);
        soft.assertThat(conta.getUserName() == usuario.getUsername());
        soft.assertThat(!usuario.getUserID().isBlank());
        soft.assertThat(usuario.getBooks().length == 0);
        soft.assertAll();

    }

    @Dado("gere um token de acesso")
    public void gere_um_token_de_acesso() {
        r = api.gerarToken(conta);
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(r.getStatusCode() == 201);
        soft.assertAll();
        token = r.getBody().as(TokenDTO.class);

    }

    @Dado("esteja autorizado")
    public void esteja_autorizado() {
        assertTrue("Falha - Usuário não autorizado.", api.validarAutorizacao(conta));

    }

    @Então("os livros alugados são listados nos dados do meu usuário")
    public void os_livros_alugados_são_listados_nos_dados_do_meu_usuário() {
        r = api.exibirDadosUsuario(token.getToken(), usuario.getUserID());
        usuario =r.body().jsonPath().getObject("", UserDTO.class);
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(r.getStatusCode() == 201);
        soft.assertAll();
    }

    @Quando("eu listar os livros disponíveis")
    public void eu_listar_os_livros_disponíveis() {
        r = api.listarLivros();
        livros = r.jsonPath().getList("books.isbn");;
    }

    @Quando("realizar o aluguel de {int} livros")
    public void realizar_o_aluguel_de_livros(int qtd) {
        RentRequestDTO rentRequest = new RentRequestDTO();
        rentRequest.setUserID(usuario.getUserID());
        List<Object> livrosDisponiveiParaAlugar = new ArrayList<>();
        livrosDisponiveiParaAlugar = util.selecionarNItensDeUmaLista((livros), 2);

        List<String> livrosSelecionados = livrosDisponiveiParaAlugar.stream()
                .map(object -> Objects.toString(object, null))
                .toList();

        rentRequest.setCollectionOfIsbns(livrosSelecionados);
        r = api.alugarLivros(token, rentRequest);
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(r.getStatusCode() == 201);
        soft.assertThat(r.body().jsonPath().getList(".books[*].isbn", BookDTO.class).containsAll(livrosSelecionados));
        soft.assertAll();
    }

}
