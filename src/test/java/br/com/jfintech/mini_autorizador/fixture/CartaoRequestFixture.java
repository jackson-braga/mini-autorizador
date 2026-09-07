package br.com.jfintech.mini_autorizador.fixture;

import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.*;

public class CartaoRequestFixture {

    public static CartaoRequest criarCartaoRequestValido() {
        return new CartaoRequest(NUMERO_CARTAO, SENHA);
    }

    public static CartaoRequest criarCartaoRequestNumeroNula() {
        return new CartaoRequest(null, SENHA);
    }

    public static CartaoRequest criarCartaoRequestNumeroVazio() {
        return new CartaoRequest("", SENHA);
    }

    public static CartaoRequest criarCartaoRequestNumeroInvalido() {
        return new CartaoRequest(NUMERO_CARTAO_INVALIDO, SENHA);
    }

    public static CartaoRequest criarCartaoRequestSenhaNula() {
        return new CartaoRequest(NUMERO_CARTAO, null);
    }

    public static CartaoRequest criarCartaoRequestSenhaVazia() {
        return new CartaoRequest(NUMERO_CARTAO, "");
    }

    public static CartaoRequest criarCartaoRequesSenhaInvalido() {
        return new CartaoRequest(NUMERO_CARTAO, SENHA_INCOMPLETA);
    }
}
