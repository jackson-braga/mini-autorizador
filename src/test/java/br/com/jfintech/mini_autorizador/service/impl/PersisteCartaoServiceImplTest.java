package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.fixture.CartaoEntityFixture;
import br.com.jfintech.mini_autorizador.fixture.FixtureConstants;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import br.com.jfintech.mini_autorizador.repository.CartaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.NUMERO_CARTAO;
import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.SENHA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("PersisteCartaoServiceImpl")
@ExtendWith(MockitoExtension.class)
class PersisteCartaoServiceImplTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private PersisteCartaoServiceImpl persisteCartaoService;

    @Test
    @DisplayName("Deve persistir cartão com sucesso")
    void devePeristirCartaoComSucesso() {
        CartaoEntity cartao = CartaoEntityFixture.criarCartaoValido();
        CartaoEntity cartaoSalvo = CartaoEntityFixture.criarCartaoSalvo();
        CartaoEntity resultadoEsperado = CartaoEntityFixture.criarCartaoSalvo();

        when(cartaoRepository.save(eq(cartao))).thenReturn(cartaoSalvo);

        CartaoEntity resultado = persisteCartaoService.persistir(cartao);

        assertEquals(resultadoEsperado, resultado);

        verify(cartaoRepository).save(any(CartaoEntity.class));
    }
}
