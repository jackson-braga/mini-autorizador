package br.com.jfintech.mini_autorizador.service;

import java.math.BigDecimal;

public interface ConsultaSaldoService {
    BigDecimal consultarSaldo(String numeroCartao);
}
