package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.fixture.CartaoEntityFixture;
import br.com.jfintech.mini_autorizador.fixture.RealizaTransacaoContextFixture;
import br.com.jfintech.mini_autorizador.fixture.TransacaoEntityFixture;
import br.com.jfintech.mini_autorizador.fixture.TransacaoRequestFixture;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import br.com.jfintech.mini_autorizador.model.entity.TransacaoEntity;
import br.com.jfintech.mini_autorizador.model.mapper.TransacaoMapper;
import br.com.jfintech.mini_autorizador.repository.TransacaoRepository;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("PersisteTransacaoStep")
@ExtendWith(MockitoExtension.class)
class PersisteTransacaoStepTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private TransacaoMapper transacaoMapper;

    @InjectMocks
    private PersisteTransacaoStep persisteTransacaoStep;

    @Test
    @DisplayName("Deve salvar a transação no repositório")
    void deveSalvarTransacao() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestValida();
        CartaoEntity cartao = CartaoEntityFixture.criarCartaoValido();
        RealizaTransacaoContext context = RealizaTransacaoContextFixture.criarContextoComDados(request, cartao);
        TransacaoEntity transacao = TransacaoEntityFixture.criarTransacaoValidaParaCartao(cartao);

        when(transacaoMapper.toEntity(request, cartao)).thenReturn(transacao);
        when(transacaoRepository.save(transacao)).thenReturn(transacao);

        persisteTransacaoStep.executar(context);

        verify(transacaoMapper).toEntity(request, cartao);
        verify(transacaoRepository).save(transacao);
    }
}
