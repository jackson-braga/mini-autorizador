package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Order(40)
public class CalculaNovoSaldoStep implements RealizaTransacaoStep {

    @Override
    public void executar(RealizaTransacaoContext context) {
        BigDecimal saldoAtual = context.getCartao().getSaldo();
        BigDecimal novoSaldo = saldoAtual.subtract(context.getRequest().valor()).setScale(2, RoundingMode.HALF_UP);
        context.getCartao().setSaldo(novoSaldo);
        context.setNovoSaldo(novoSaldo);
    }
}
