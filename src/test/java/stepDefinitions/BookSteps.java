package stepDefinitions;

import DTO.AccountDTO;
import DTO.RentRequestDTO;
import DTO.TokenDTO;
import DTO.UserDTO;
import callers.ApiCaller;
import core.Utils;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookSteps {

    ApiCaller api = new ApiCaller();
    Utils util = new Utils();
    UserDTO usuario = new UserDTO();
    TokenDTO token = new TokenDTO();
    List<String> livros;
    private Response response;

    @Quando("eu listar os livros disponíveis")
    public void eu_listar_os_livros_disponíveis() {
        livros = api.listarLivros();
    }

    @Quando("realizar o aluguel de {int} livros")
    public void realizar_o_aluguel_de_livros(int qtd) {
        RentRequestDTO rentRequest = new RentRequestDTO();
        rentRequest.setUserID(usuario.getUserID());
        List<Object> livrosParaAlugar = new ArrayList<>();
        livrosParaAlugar = util.selecionarNItensDeUmaLista((livros), 2);

        List<String> strings = livrosParaAlugar.stream()
                .map(object -> Objects.toString(object, null))
                .toList();


        rentRequest.setCollectionOfIsbns(strings);
        api.alugarLivros(token, rentRequest);
    }

}
