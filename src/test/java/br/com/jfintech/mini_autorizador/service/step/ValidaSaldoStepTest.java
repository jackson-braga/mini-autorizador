package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.exception.TransacaoInvalidaException;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ValidaSaldoStep")
@ExtendWith(MockitoExtension.class)
class ValidaSaldoStepTest {

    @InjectMocks
    private ValidaSaldoStep validaSaldoStep;

    @Test
    @DisplayName("Deve validar o saldo quando o valor da transação estiver dentro do limite")
    void deveValidarSaldoQuandoValorForAceito() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestValida();
        CartaoEntity cartao = CartaoEntityFixture.criarCartaoValido();
        RealizaTransacaoContext context = RealizaTransacaoContextFixture.criarContextoComDados(request, cartao);

        assertDoesNotThrow(() -> validaSaldoStep.executar(context));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o saldo for insuficiente")
    void deveLancarExcecaoQuandoSaldoForInsuficiente() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestSaldoInsuficiente();
        CartaoEntity cartao = CartaoEntityFixture.criarCartaoValido();
        RealizaTransacaoContext context = RealizaTransacaoContextFixture.criarContextoComDados(request, cartao);

        TransacaoInvalidaException exception = assertThrows(
                TransacaoInvalidaException.class,
                () -> validaSaldoStep.executar(context)
        );

        assertEquals("Saldo insuficiente", exception.getMessage());
        assertEquals(TransacaoInvalidaException.SALDO_INSUFICIENTE, exception.getPayload());
    }
}
