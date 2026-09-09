package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.fixture.CartaoEntityFixture;
import br.com.jfintech.mini_autorizador.fixture.RealizaTransacaoContextFixture;
import br.com.jfintech.mini_autorizador.fixture.TransacaoRequestFixture;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("CalculaNovoSaldoStep")
@ExtendWith(MockitoExtension.class)
class CalculaNovoSaldoStepTest {

    @InjectMocks
    private CalculaNovoSaldoStep calculaNovoSaldoStep;

    @Test
    @DisplayName("Deve calcular o novo saldo após a transação")
    void deveCalcularNovoSaldo() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestValida();
        CartaoEntity cartao = CartaoEntityFixture.criarCartaoValido();
        RealizaTransacaoContext context = RealizaTransacaoContextFixture.criarContextoComDados(request, cartao);
        RealizaTransacaoContext contextEsperado = RealizaTransacaoContextFixture.criarContextoComDados(request, cartao, SALDO_CALCULADO);

        calculaNovoSaldoStep.executar(context);

        assertEquals(contextEsperado, context);
        assertEquals(0, SALDO_CALCULADO.compareTo(context.getNovoSaldo()));
        assertEquals(0, SALDO_CALCULADO.compareTo(context.getCartao().getSaldo()));
    }
}
