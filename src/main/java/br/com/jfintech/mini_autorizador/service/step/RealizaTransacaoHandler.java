package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;

public abstract class RealizaTransacaoHandler {

    private final RealizaTransacaoHandler next;

    protected RealizaTransacaoHandler(RealizaTransacaoHandler next) {
        this.next = next;
    }

    public final RealizaTransacaoContext executar(RealizaTransacaoContext context) {
        RealizaTransacaoContext processed = processar(context);
        if (next == null) {
            return processed;
        }
        return next.executar(processed);
    }

    protected abstract RealizaTransacaoContext processar(RealizaTransacaoContext context);
}
