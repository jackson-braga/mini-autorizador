package br.com.jfintech.mini_autorizador.fixture;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class FixtureConstants {

    public static final String NUMERO_CARTAO = "1234567890123456";
    public static final String NUMERO_CARTAO_INVALIDO = "123456";
    public static final String NUMERO_CARTAO_INEXISTENTE = "0000111122223333";
    public static final String SENHA = "1234";
    public static final String SENHA_INCOMPLETA = "123";
    public static final String SENHA_INVALIDA = "0000";
    public static final BigDecimal SALDO_INICIAL = new BigDecimal("500.00");
    public static final BigDecimal VALOR_TRANSACAO = new BigDecimal("50.00");
    public static final BigDecimal VALOR_TRANSACAO_EXEDIDA = new BigDecimal("1000.00");
    public static final BigDecimal VALOR_NEGATIVO_TRANSACAO = new BigDecimal("-10.00");

}
