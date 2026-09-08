package br.com.jfintech.mini_autorizador.fixture;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.*;

public class TransacaoRequestFixture {

    public static TransacaoRequest criarTransacaoRequestValida() {
        return new TransacaoRequest(NUMERO_CARTAO, SENHA, VALOR_TRANSACAO);
    }

    public static TransacaoRequest criarTransacaoRequestSaldoInsuficiente() {
        return new TransacaoRequest(NUMERO_CARTAO, SENHA, VALOR_TRANSACAO_EXEDIDA);
    }

    public static TransacaoRequest criarTransacaoRequestComSenhaInvalida() {
        return new TransacaoRequest(NUMERO_CARTAO, SENHA_INVALIDA, VALOR_TRANSACAO);
    }

    public static TransacaoRequest criarTransacaoRequestComCartaoInexistente() {
        return new TransacaoRequest(NUMERO_CARTAO_INEXISTENTE, SENHA, VALOR_TRANSACAO);
    }

    public static TransacaoRequest criarTransacaoRequestComNumeroCartaoInvalido() {
        return new TransacaoRequest(NUMERO_CARTAO_INVALIDO, SENHA, VALOR_TRANSACAO);
    }

    public static TransacaoRequest criarTransacaoRequestComNumeroCartaoNulo() {
        return new TransacaoRequest(null, SENHA, VALOR_TRANSACAO);
    }

    public static TransacaoRequest criarTransacaoRequestComNumeroCartaoVazio() {
        return new TransacaoRequest("", SENHA, VALOR_TRANSACAO);
    }

    public static TransacaoRequest criarTransacaoRequestComSenhaIncompleta() {
        return new TransacaoRequest(NUMERO_CARTAO, SENHA_INCOMPLETA, VALOR_TRANSACAO);
    }

    public static TransacaoRequest criarTransacaoRequestComSenhaNulo() {
        return new TransacaoRequest(NUMERO_CARTAO, null, VALOR_TRANSACAO);
    }

    public static TransacaoRequest criarTransacaoRequestComSenhaVazio() {
        return new TransacaoRequest(NUMERO_CARTAO, "", VALOR_TRANSACAO);
    }

    public static TransacaoRequest criarTransacaoRequestComValorNegativo() {
        return new TransacaoRequest(NUMERO_CARTAO, SENHA, VALOR_NEGATIVO_TRANSACAO);
    }

    public static TransacaoRequest criarTransacaoRequestComValorNulo() {
        return new TransacaoRequest(NUMERO_CARTAO, SENHA, null);
    }
}
