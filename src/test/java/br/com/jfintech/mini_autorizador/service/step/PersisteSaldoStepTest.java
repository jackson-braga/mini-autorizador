package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
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

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("PersisteSaldoStep")
@ExtendWith(MockitoExtension.class)
class PersisteSaldoStepTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private PersisteSaldoStep persisteSaldoStep;

    @Test
    @DisplayName("Deve atualizar o saldo do cartão no repositório")
    void devePersistirSaldoDoCartao() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestValida();
        CartaoEntity cartao = CartaoEntityFixture.criarCartaoValido();
        RealizaTransacaoContext context = RealizaTransacaoContextFixture.criarContextoComDados(request, cartao);
        context.setNovoSaldo(SALDO_CALCULADO);

        when(cartaoRepository.atualizarSaldoPorNumeroCartao(NUMERO_CARTAO, SALDO_CALCULADO)).thenReturn(1);

        persisteSaldoStep.executar(context);

        verify(cartaoRepository).atualizarSaldoPorNumeroCartao(NUMERO_CARTAO, SALDO_CALCULADO);
    }
}
