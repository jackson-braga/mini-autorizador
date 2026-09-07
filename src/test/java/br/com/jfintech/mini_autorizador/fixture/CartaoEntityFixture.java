package br.com.jfintech.mini_autorizador.fixture;

import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;

import java.math.BigDecimal;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.*;

public class CartaoEntityFixture {

    public static CartaoEntity criarCartaoValido() {
        return CartaoEntity.builder()
                .numeroCartao(NUMERO_CARTAO)
                .senha(SENHA)
                .saldo(SALDO_INICIAL)
                .build();
    }

    public static CartaoEntity criarCartaoSalvo() {
        return CartaoEntity.builder()
                .numeroCartao(NUMERO_CARTAO)
                .senha(SENHA)
                .saldo(SALDO_INICIAL)
                .build();
    }
}
