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

@DisplayName("ValidaSenhaStep")
@ExtendWith(MockitoExtension.class)
class ValidaSenhaStepTest {

    @InjectMocks
    private ValidaSenhaStep validaSenhaStep;

    @Test
    @DisplayName("Deve validar a senha quando ela for correta")
    void deveValidarSenhaQuandoCorreta() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestValida();
        CartaoEntity cartao = CartaoEntityFixture.criarCartaoValido();
        RealizaTransacaoContext context = RealizaTransacaoContextFixture.criarContextoComDados(request, cartao);

        assertDoesNotThrow(() -> validaSenhaStep.executar(context));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha estiver incorreta")
    void deveLancarExcecaoQuandoSenhaIncorreta() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestComSenhaInvalida();
        CartaoEntity cartao = CartaoEntityFixture.criarCartaoValido();
        RealizaTransacaoContext context = RealizaTransacaoContextFixture.criarContextoComDados(request, cartao);

        TransacaoInvalidaException exception = assertThrows(
                TransacaoInvalidaException.class,
                () -> validaSenhaStep.executar(context)
        );

        assertEquals("Senha inválida", exception.getMessage());
        assertEquals(TransacaoInvalidaException.SENHA_INVALIDA, exception.getPayload());
    }
}
