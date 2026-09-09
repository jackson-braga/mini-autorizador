package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.fixture.TransacaoRequestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("RealizaTransacaoServiceImpl")
@ExtendWith(MockitoExtension.class)
class RealizaTransacaoServiceImplTest {

    @Mock
    private RealizaTransacaoChain realizaTransacaoChain;

    @InjectMocks
    private RealizaTransacaoServiceImpl realizaTransacaoService;

    @Test
    @DisplayName("Deve delegar a execução para o chain de transação")
    void deveDelegarParaChain() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestValida();

        when(realizaTransacaoChain.processar(request)).thenReturn("OK");

        String resultado = realizaTransacaoService.realizarTransacao(request);

        assertEquals("OK", resultado);

        verify(realizaTransacaoChain).processar(request);
    }
}
