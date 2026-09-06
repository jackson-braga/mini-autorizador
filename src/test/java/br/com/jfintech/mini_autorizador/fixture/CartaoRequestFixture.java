package br.com.jfintech.mini_autorizador.fixture;

import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;

public class CartaoRequestFixture {

    public static final String NUMERO_CARTAO = "1234567890123456";
    public static final String SENHA = "1234";

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
        return new CartaoRequest("123", SENHA);
    }

    public static CartaoRequest criarCartaoRequestSenhaNula() {
        return new CartaoRequest(NUMERO_CARTAO, null);
    }

    public static CartaoRequest criarCartaoRequestSenhaVazia() {
        return new CartaoRequest(NUMERO_CARTAO, "");
    }

    public static CartaoRequest criarCartaoRequesSenhaInvalido() {
        return new CartaoRequest(NUMERO_CARTAO, "123");
    }
}
