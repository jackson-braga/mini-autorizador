package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.repository.CartaoRepository;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(50)
@RequiredArgsConstructor
public class PersisteSaldoStep implements RealizaTransacaoStep {

    private final CartaoRepository cartaoRepository;

    @Override
    public void executar(RealizaTransacaoContext context) {
        cartaoRepository.atualizarSaldoPorNumeroCartao(
                context.getCartao().getNumeroCartao(),
                context.getNovoSaldo()
        );
    }
}
