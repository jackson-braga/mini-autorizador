package br.com.jfintech.mini_autorizador.fixture;

import br.com.jfintech.mini_autorizador.controller.v1.response.CartaoCadastradoResponse;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.NUMERO_CARTAO;
import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.SENHA;

public class CartaoCadastradoResponseFixture {

    public static CartaoCadastradoResponse criarCartaoCadastradoResponseValido() {
        return new CartaoCadastradoResponse(NUMERO_CARTAO, SENHA);
    }
}
