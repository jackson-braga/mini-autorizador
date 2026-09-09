package br.com.jfintech.mini_autorizador.fixture;

import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import br.com.jfintech.mini_autorizador.model.entity.TransacaoEntity;

import java.time.LocalDateTime;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.VALOR_TRANSACAO;

public class TransacaoEntityFixture {

    public static TransacaoEntity criarTransacaoValidaParaCartao(CartaoEntity cartao) {
        return TransacaoEntity.builder()
                .cartao(cartao)
                .valor(VALOR_TRANSACAO)
                .build();
    }

}
