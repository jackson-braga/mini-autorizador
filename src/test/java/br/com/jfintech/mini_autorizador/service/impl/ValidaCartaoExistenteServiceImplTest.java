package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;
import br.com.jfintech.mini_autorizador.exception.CartaoExistenteException;
import br.com.jfintech.mini_autorizador.fixture.CartaoRequestFixture;
import br.com.jfintech.mini_autorizador.repository.CartaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.NUMERO_CARTAO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("ValidaCartaoExistenteServiceImpl")
@ExtendWith(MockitoExtension.class)
class ValidaCartaoExistenteServiceImplTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private ValidaCartaoExistenteServiceImpl validaCartaoExistenteService;

    @Test
    @DisplayName("Deve validar sem exceção quando cartão não existe")
    void deveValidarSemExcecaoQuandoCartaoNaoExiste() {
        CartaoRequest request = CartaoRequestFixture.criarCartaoRequestValido();

        when(cartaoRepository.existsById(eq(NUMERO_CARTAO))).thenReturn(false);

        assertDoesNotThrow(() -> validaCartaoExistenteService.validar(request));

        verify(cartaoRepository).existsById(eq(NUMERO_CARTAO));
    }

    @Test
    @DisplayName("Deve lançar CartaoExistenteException quando cartão já existe")
    void deveLancarCartaoExistenteExceptionQuandoCartaoJaExiste() {
        CartaoRequest request = CartaoRequestFixture.criarCartaoRequestValido();
        CartaoRequest payloadEsperado = CartaoRequestFixture.criarCartaoRequestValido();

        when(cartaoRepository.existsById(eq(NUMERO_CARTAO))).thenReturn(true);

        CartaoExistenteException exception = assertThrows(
                CartaoExistenteException.class,
                () -> validaCartaoExistenteService.validar(request)
        );

        assertEquals(payloadEsperado, exception.getPayload());

        verify(cartaoRepository).existsById(eq(NUMERO_CARTAO));
    }
}
