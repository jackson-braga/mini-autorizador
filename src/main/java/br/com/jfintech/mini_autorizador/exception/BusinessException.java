package br.com.jfintech.mini_autorizador.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        this(message, null);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
