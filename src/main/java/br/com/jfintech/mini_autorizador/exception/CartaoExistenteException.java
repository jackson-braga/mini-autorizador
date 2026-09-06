package br.com.jfintech.mini_autorizador.exception;

import lombok.Getter;

@Getter
public class CartaoExistenteException extends BusinessException {
    private final Object payload;

    public CartaoExistenteException(String message, Object payload) {
        super(message);
        this.payload = payload;
    }

    public CartaoExistenteException(String message, Throwable cause, Object payload) {
        super(message, cause);
        this.payload = payload;
    }
}
