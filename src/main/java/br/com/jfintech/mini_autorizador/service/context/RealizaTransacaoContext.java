package br.com.jfintech.mini_autorizador.service.context;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;

import java.math.BigDecimal;

public record RealizaTransacaoContext(TransacaoRequest request, CartaoEntity cartao, BigDecimal novoSaldo) {

    public RealizaTransacaoContext withRequest(TransacaoRequest request) {
        return new RealizaTransacaoContext(request, this.cartao, this.novoSaldo);
    }

    public RealizaTransacaoContext withCartao(CartaoEntity cartao) {
        return new RealizaTransacaoContext(this.request, cartao, this.novoSaldo);
    }

    public RealizaTransacaoContext withNovoSaldo(BigDecimal novoSaldo) {
        return new RealizaTransacaoContext(this.request, this.cartao, novoSaldo);
    }
}
