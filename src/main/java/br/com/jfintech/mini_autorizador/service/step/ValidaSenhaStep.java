package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.exception.TransacaoInvalidaException;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(20)
public class ValidaSenhaStep implements RealizaTransacaoStep {

    @Override
    public void executar(RealizaTransacaoContext context) {
        if (!context.getCartao().getSenha().equals(context.getRequest().senhaCartao())) {
            throw new TransacaoInvalidaException("Senha inválida", TransacaoInvalidaException.SENHA_INVALIDA);
        }
    }
}
