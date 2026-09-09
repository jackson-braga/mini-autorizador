package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.fixture.RealizaTransacaoContextFixture;
import br.com.jfintech.mini_autorizador.fixture.TransacaoRequestFixture;
import br.com.jfintech.mini_autorizador.model.mapper.TransacaoMapper;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import br.com.jfintech.mini_autorizador.service.step.RealizaTransacaoStep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("RealizarTransacaoChain")
@ExtendWith(MockitoExtension.class)
class RealizaTransacaoChainTest {

    @Mock
    private RealizaTransacaoStep stepA;

    @Mock
    private RealizaTransacaoStep stepB;

    @Mock
    private TransacaoMapper transacaoMapper;

    @Test
    @DisplayName("Deve criar o contexto e executar todos os passos da transação")
    void deveExecutarTodosOsPassos() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestValida();
        RealizaTransacaoContext context = RealizaTransacaoContextFixture.criarContextoComDados(request);
        RealizaTransacaoChain chain = new RealizaTransacaoChain(List.of(stepA, stepB), transacaoMapper);

        when(transacaoMapper.toContext(request, BigDecimal.ZERO)).thenReturn(context);

        String resultado = chain.processar(request);

        assertEquals("OK", resultado);

        verify(transacaoMapper).toContext(request, BigDecimal.ZERO);
        verify(stepA).executar(context);
        verify(stepB).executar(context);
    }
}
