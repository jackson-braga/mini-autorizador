package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.exception.TransacaoInvalidaException;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(20)
public class ValidaSenhaStep implements RealizaTransacaoStep {

    @Override
    public RealizaTransacaoContext executar(RealizaTransacaoContext context) {
        if (!context.cartao().getSenha().equals(context.request().senhaCartao())) {
            throw new TransacaoInvalidaException("Senha inválida", TransacaoInvalidaException.SENHA_INVALIDA);
        }

        return context;
    }
}
