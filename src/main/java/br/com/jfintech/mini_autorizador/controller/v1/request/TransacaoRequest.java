package br.com.jfintech.mini_autorizador.controller.v1.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record TransacaoRequest(
        @NotBlank(message = "numeroCartao não pode ser nulo ou vazio")
        @Pattern(regexp = "\\d{16}", message = "numeroCartao deve conter 16 dígitos numéricos")
        String numeroCartao,

        @NotBlank(message = "senhaCartao não pode ser nula ou vazia")
        @Pattern(regexp = "\\d{4}", message = "senhaCartao deve conter 4 dígitos numéricos")
        String senhaCartao,

        @NotNull(message = "valor não pode ser nulo")
        @DecimalMin(value = "0.01", message = "valor deve ser maior que zero")
        BigDecimal valor
) {
}
