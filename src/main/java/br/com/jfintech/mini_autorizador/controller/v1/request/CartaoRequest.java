package br.com.jfintech.mini_autorizador.controller.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CartaoRequest(
        @NotBlank(message = "numeroCartao não pode ser nulo ou vazio")
        @Pattern(regexp = "\\d{16}", message = "numeroCartao deve conter 16 dígitos numéricos")
        String numeroCartao,

        @NotBlank(message = "senha não pode ser nula ou vazia")
        @Pattern(regexp = "\\d{4}", message = "senha deve conter 4 dígitos numéricos")
        String senha
) {
}
