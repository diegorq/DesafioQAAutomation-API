package core;


import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {


    public List<Object> selecionarNItensDeUmaLista(List<String> l, int n) {
        List<Object> l1 = new ArrayList<>(l);
        Collections.shuffle(l1);
        return n > l1.size() ? l1.subList(0, l1.size()) : l1.subList(0, n);
    }

    public String gerarNomeUsuario() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public String gerarSenha() {
        String set1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String set2 = "abcdefghijklmnopqrstuvwxyz";
        String set3 = "0123456789";
        String set4 = "!@#$%&*";
        return RandomStringUtils.randomAlphanumeric(6) +
                RandomStringUtils.random(1, set1) +
                RandomStringUtils.random(1, set2) +
                RandomStringUtils.random(1, set3) +
                RandomStringUtils.random(1, set4);
    }


}
