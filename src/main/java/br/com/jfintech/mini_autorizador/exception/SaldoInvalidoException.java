package br.com.jfintech.mini_autorizador.exception;

import lombok.Getter;

@Getter
public class SaldoInvalidoException extends BusinessException {
    private final String payload;

    public SaldoInvalidoException(String message, Throwable cause, String payload) {
        super(message, cause);
        this.payload = payload;
    }
}
