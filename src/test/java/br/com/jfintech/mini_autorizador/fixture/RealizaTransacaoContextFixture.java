package br.com.jfintech.mini_autorizador.fixture;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;

import java.math.BigDecimal;

public class RealizaTransacaoContextFixture {

    public static RealizaTransacaoContext criarContextoComDados(TransacaoRequest request) {

        return criarContextoComDados(request, null, BigDecimal.ZERO);
    }

    public static RealizaTransacaoContext criarContextoComDados(TransacaoRequest request, CartaoEntity cartao) {

        return criarContextoComDados(request, cartao, BigDecimal.ZERO);
    }

    public static RealizaTransacaoContext criarContextoComDados(TransacaoRequest request, CartaoEntity cartao, BigDecimal novoSaldo) {

        return RealizaTransacaoContext.builder()
                .request(request)
                .cartao(cartao)
                .novoSaldo(novoSaldo)
                .build();
    }
}
