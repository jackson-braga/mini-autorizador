package br.com.jfintech.mini_autorizador.controller.v1.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponse(Long id, String numeroCartao, BigDecimal valor, LocalDateTime dataHoraTransacao) {
}
