package br.com.jfintech.mini_autorizador.fixture;

import br.com.jfintech.mini_autorizador.controller.v1.response.CartaoResponse;

public class CartaoResponseFixture {

    public static CartaoResponse criarCartaoResponseValido() {
        return new CartaoResponse("1234", "6549873025634501");
    }
}
