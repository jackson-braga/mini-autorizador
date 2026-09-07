package br.com.jfintech.mini_autorizador.service;

import java.math.BigDecimal;

public interface ConsultaCartaoService {
    BigDecimal consultarSaldo(String numeroCartao);
}
