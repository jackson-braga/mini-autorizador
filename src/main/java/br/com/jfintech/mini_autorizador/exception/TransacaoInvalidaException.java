package br.com.jfintech.mini_autorizador.exception;

import lombok.Getter;

@Getter
public class TransacaoInvalidaException extends BusinessException {

    public static final String SALDO_INSUFICIENTE = "SALDO_INSUFICIENTE";
    public static final String CARTAO_INEXISTENTE = "CARTAO_INEXISTENTE";
    public static final String SENHA_INVALIDA = "SENHA_INVALIDA";

    private final String payload;

    public TransacaoInvalidaException(String message, String payload) {
        super(message);
        this.payload = payload;
    }

    public TransacaoInvalidaException(String message, Throwable cause, String payload) {
        super(message, cause);
        this.payload = payload;
    }
}
