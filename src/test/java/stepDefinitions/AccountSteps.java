package stepDefinitions;

import DTO.AccountDTO;
import DTO.TokenDTO;
import DTO.UserDTO;
import callers.ApiCaller;
import core.Utils;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.restassured.response.Response;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class AccountSteps {
 ApiCaller api = new ApiCaller();
    Utils util = new Utils();
    UserDTO usuario = new UserDTO();
    AccountDTO conta = new AccountDTO();
    TokenDTO token = new TokenDTO();

    @Dado("que eu cadastre um novo usuário")
    public void que_eu_cadastre_um_novo_usuário() {
        conta = new AccountDTO(util.gerarNomeUsuario(), util.gerarSenha());
        usuario = api.criarUsuario(conta, 201);
    }

    @Dado("gere um token de acesso")
    public void gere_um_token_de_acesso() {
        token = api.gerarToken(conta, 200);
    }

    @Dado("esteja autorizado")
    public void esteja_autorizado() {
        assertTrue("Falha - Usuário não autorizado.", api.validarAutorizacao(conta, 200));

    }

    @Então("os livros alugados são listados nos dados do meu usuário")
    public void os_livros_alugados_são_listados_nos_dados_do_meu_usuário() {
        usuario = api.exibirDadosUsuario(token.getToken(), usuario.getUserID());
    }

}
