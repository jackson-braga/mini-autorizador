package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;
import br.com.jfintech.mini_autorizador.controller.v1.response.CartaoCadastradoResponse;
import br.com.jfintech.mini_autorizador.exception.CartaoExistenteException;
import br.com.jfintech.mini_autorizador.fixture.CartaoCadastradoResponseFixture;
import br.com.jfintech.mini_autorizador.fixture.CartaoEntityFixture;
import br.com.jfintech.mini_autorizador.fixture.CartaoRequestFixture;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import br.com.jfintech.mini_autorizador.model.mapper.CartaoMapper;
import br.com.jfintech.mini_autorizador.service.PersisteCartaoService;
import br.com.jfintech.mini_autorizador.service.ValidaCartaoExistenteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.SALDO_INICIAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("CadastraCartaoServiceImpl")
@ExtendWith(MockitoExtension.class)
class CadastraCartaoServiceImplTest {

    @Mock
    private ValidaCartaoExistenteService validaCartaoExistenteService;

    @Mock
    private CartaoMapper cartaoMapper;

    @Mock
    private PersisteCartaoService persisteCartaoService;

    @InjectMocks
    private CadastraCartaoServiceImpl cadastraCartaoService;

    @Test
    @DisplayName("Deve cadastrar cartão com sucesso")
    void deveCadastrarCartaoComSucesso() {
        CartaoRequest request = CartaoRequestFixture.criarCartaoRequestValido();
        CartaoEntity cartaoEntity = CartaoEntityFixture.criarCartaoValido();
        CartaoCadastradoResponse response = CartaoCadastradoResponseFixture.criarCartaoCadastradoResponseValido();
        CartaoCadastradoResponse resultadoEsperado = CartaoCadastradoResponseFixture.criarCartaoCadastradoResponseValido();

        doNothing().when(validaCartaoExistenteService).validar(eq(request));
        when(cartaoMapper.toEntity(eq(request), eq(SALDO_INICIAL))).thenReturn(cartaoEntity);
        when(persisteCartaoService.persistir(eq(cartaoEntity))).thenReturn(cartaoEntity);
        when(cartaoMapper.toCadastradoResponse(eq(cartaoEntity))).thenReturn(response);

        CartaoCadastradoResponse resultado = cadastraCartaoService.cadastrar(request);

        assertEquals(resultadoEsperado, resultado);

        verify(validaCartaoExistenteService).validar(eq(request));
        verify(cartaoMapper).toEntity(eq(request), eq(SALDO_INICIAL));
        verify(persisteCartaoService).persistir(eq(cartaoEntity));
        verify(cartaoMapper).toCadastradoResponse(eq(cartaoEntity));
    }

    @Test
    @DisplayName("Deve lançar CartaoExistenteException quando cartão já existe")
    void deveLancarCartaoExistenteExceptionQuandoCartaoJaExiste() {
        CartaoRequest request = CartaoRequestFixture.criarCartaoRequestValido();

        doThrow(new CartaoExistenteException("Cartão já existe", request))
                .when(validaCartaoExistenteService)
                .validar(eq(request));

        CartaoExistenteException exception = assertThrows(
                CartaoExistenteException.class,
                () -> cadastraCartaoService.cadastrar(request)
        );

        assertEquals("Cartão já existe", exception.getMessage());

        verify(validaCartaoExistenteService).validar(eq(request));
        verifyNoInteractions(persisteCartaoService, cartaoMapper);
    }
}
