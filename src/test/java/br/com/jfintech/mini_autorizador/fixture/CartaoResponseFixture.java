package br.com.jfintech.mini_autorizador.fixture;

import br.com.jfintech.mini_autorizador.controller.v1.response.CartaoResponse;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.NUMERO_CARTAO;
import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.SENHA;

public class CartaoResponseFixture {

    public static CartaoResponse criarCartaoResponseValido() {
        return new CartaoResponse(SENHA, NUMERO_CARTAO);
    }
}
