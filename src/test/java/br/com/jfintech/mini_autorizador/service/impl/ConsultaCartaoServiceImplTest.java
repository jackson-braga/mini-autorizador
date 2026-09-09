package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.exception.NotFoundException;
import br.com.jfintech.mini_autorizador.fixture.FixtureConstants;
import br.com.jfintech.mini_autorizador.repository.CartaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("ConsultaCartaoServiceImpl")
@ExtendWith(MockitoExtension.class)
class ConsultaCartaoServiceImplTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private ConsultaCartaoServiceImpl consultaCartaoService;

    @Test
    @DisplayName("Deve consultar saldo do cartão quando ele existir")
    void deveConsultarSaldoQuandoCartaoExistir() {
        BigDecimal saldoEsperado = new BigDecimal("500.00");

        when(cartaoRepository.findSaldoByNumeroCartao(eq(NUMERO_CARTAO))).thenReturn(Optional.of(SALDO_INICIAL));

        BigDecimal saldo = consultaCartaoService.consultarSaldo(NUMERO_CARTAO);

        assertEquals(0, saldoEsperado.compareTo(saldo));

        verify(cartaoRepository).findSaldoByNumeroCartao(eq(NUMERO_CARTAO));
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando cartão não existir")
    void deveLancarNotFoundExceptionQuandoCartaoNaoExistir() {

        when(cartaoRepository.findSaldoByNumeroCartao(eq(NUMERO_CARTAO_INEXISTENTE))).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> consultaCartaoService.consultarSaldo(NUMERO_CARTAO_INEXISTENTE)
        );

        assertEquals("Cartão não encontrado", exception.getMessage());

        verify(cartaoRepository).findSaldoByNumeroCartao(eq(NUMERO_CARTAO_INEXISTENTE));
    }
}
