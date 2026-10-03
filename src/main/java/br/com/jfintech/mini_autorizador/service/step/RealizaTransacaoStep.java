package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;

public interface RealizaTransacaoStep {
    RealizaTransacaoContext executar(RealizaTransacaoContext context);
}
