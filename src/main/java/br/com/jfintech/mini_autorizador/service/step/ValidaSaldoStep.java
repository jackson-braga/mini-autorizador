package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.exception.TransacaoInvalidaException;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(30)
public class ValidaSaldoStep implements RealizaTransacaoStep {

    @Override
    public void executar(RealizaTransacaoContext context) {
        if (context.getCartao().getSaldo().compareTo(context.getRequest().valor()) < 0) {
            throw new TransacaoInvalidaException("Saldo insuficiente", TransacaoInvalidaException.SALDO_INSUFICIENTE);
        }
    }
}
