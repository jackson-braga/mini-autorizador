package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.exception.TransacaoInvalidaException;
import br.com.jfintech.mini_autorizador.repository.CartaoRepository;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(10)
@RequiredArgsConstructor
public class BuscaCartaoStep implements RealizaTransacaoStep {

    private final CartaoRepository cartaoRepository;

    @Override
    public void executar(RealizaTransacaoContext context) {
        context.setCartao(
                cartaoRepository.findById(context.getRequest().numeroCartao())
                        .orElseThrow(() -> new TransacaoInvalidaException("Cartão inexistente", TransacaoInvalidaException.CARTAO_INEXISTENTE))
        );
    }
}
