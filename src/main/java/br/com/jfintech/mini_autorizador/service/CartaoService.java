package br.com.jfintech.mini_autorizador.service;

import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;
import br.com.jfintech.mini_autorizador.controller.v1.response.CartaoResponse;

import java.math.BigDecimal;

public interface CartaoService {
    CartaoResponse criarCartao(CartaoRequest request);
    BigDecimal obterSaldo(String numeroCartao);
}
