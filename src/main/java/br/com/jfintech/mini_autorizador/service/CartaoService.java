package br.com.jfintech.mini_autorizador.service;

import java.math.BigDecimal;

public interface CartaoService {
    BigDecimal obterSaldo(String numeroCartao);
}
