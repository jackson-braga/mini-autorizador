package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;

public interface RealizaTransacaoStep {
    void executar(RealizaTransacaoContext context);
}
