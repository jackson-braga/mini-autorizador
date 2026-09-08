package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.exception.TransacaoInvalidaException;
import br.com.jfintech.mini_autorizador.fixture.CartaoEntityFixture;
import br.com.jfintech.mini_autorizador.fixture.RealizaTransacaoContextFixture;
import br.com.jfintech.mini_autorizador.fixture.TransacaoRequestFixture;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import br.com.jfintech.mini_autorizador.repository.CartaoRepository;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.NUMERO_CARTAO;
import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.NUMERO_CARTAO_INEXISTENTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("BuscaCartaoStep")
@ExtendWith(MockitoExtension.class)
class BuscaCartaoStepTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private BuscaCartaoStep buscaCartaoStep;

    @Test
    @DisplayName("Deve buscar o cartão e preencher o contexto quando o cartão existir")
    void deveBuscarCartaoQuandoExistir() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestValida();
        CartaoEntity cartao = CartaoEntityFixture.criarCartaoValido();
        RealizaTransacaoContext context = RealizaTransacaoContextFixture.criarContextoComDados(request, null);
        RealizaTransacaoContext contextEsperado = RealizaTransacaoContextFixture.criarContextoComDados(request, cartao);

        when(cartaoRepository.findById(NUMERO_CARTAO)).thenReturn(Optional.of(cartao));

        buscaCartaoStep.executar(context);

        assertEquals(contextEsperado, context);

        verify(cartaoRepository).findById(NUMERO_CARTAO);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o cartão não existir")
    void deveLancarExcecaoQuandoCartaoNaoExistir() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestComCartaoInexistente();
        RealizaTransacaoContext context = RealizaTransacaoContextFixture.criarContextoComDados(request, null);

        when(cartaoRepository.findById(NUMERO_CARTAO_INEXISTENTE)).thenReturn(Optional.empty());

        TransacaoInvalidaException exception = assertThrows(
                TransacaoInvalidaException.class,
                () -> buscaCartaoStep.executar(context)
        );

        assertEquals("Cartão inexistente", exception.getMessage());
        assertEquals(TransacaoInvalidaException.CARTAO_INEXISTENTE, exception.getPayload());

        verify(cartaoRepository).findById(NUMERO_CARTAO_INEXISTENTE);
    }
}
