package br.com.jfintech.mini_autorizador.fixture;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;

import java.math.BigDecimal;

public class TransacaoRequestFixture {

    public static final String NUMERO_CARTAO = "6549873025634501";
    public static final String SENHA_CARTAO = "1234";
    public static final BigDecimal VALOR = new BigDecimal("10.00");

    public static TransacaoRequest criarTransacaoRequestValida() {
        return new TransacaoRequest(NUMERO_CARTAO, SENHA_CARTAO, VALOR);
    }

    public static TransacaoRequest criarTransacaoRequestSaldoInsuficiente() {
        return new TransacaoRequest(NUMERO_CARTAO, SENHA_CARTAO, new BigDecimal("1000.00"));
    }

    public static TransacaoRequest criarTransacaoRequestComSenhaInvalida() {
        return new TransacaoRequest(NUMERO_CARTAO, "0000", VALOR);
    }

    public static TransacaoRequest criarTransacaoRequestComCartaoInexistente() {
        return new TransacaoRequest("0000111122223333", SENHA_CARTAO, VALOR);
    }

    public static TransacaoRequest criarTransacaoRequestComNumeroCartaoInvalido() {
        return new TransacaoRequest("1234567", SENHA_CARTAO, VALOR);
    }

    public static TransacaoRequest criarTransacaoRequestComNumeroCartaoNulo() {
        return new TransacaoRequest(null, SENHA_CARTAO, VALOR);
    }

    public static TransacaoRequest criarTransacaoRequestComNumeroCartaoVazio() {
        return new TransacaoRequest("", SENHA_CARTAO, VALOR);
    }

    public static TransacaoRequest criarTransacaoRequestComSenhaIncompleta() {
        return new TransacaoRequest(NUMERO_CARTAO, "00", VALOR);
    }

    public static TransacaoRequest criarTransacaoRequestComSenhaNulo() {
        return new TransacaoRequest(NUMERO_CARTAO, null, VALOR);
    }

    public static TransacaoRequest criarTransacaoRequestComSenhaVazio() {
        return new TransacaoRequest(NUMERO_CARTAO, "", VALOR);
    }

    public static TransacaoRequest criarTransacaoRequestComValorNegativo() {
        return new TransacaoRequest(NUMERO_CARTAO, SENHA_CARTAO, new BigDecimal("-10.00"));
    }

    public static TransacaoRequest criarTransacaoRequestComValorNulo() {
        return new TransacaoRequest(NUMERO_CARTAO, SENHA_CARTAO, null);
    }
}
