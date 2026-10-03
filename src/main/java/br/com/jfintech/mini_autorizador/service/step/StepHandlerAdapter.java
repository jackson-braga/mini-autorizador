package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;

public class StepHandlerAdapter extends RealizaTransacaoHandler {

    private final RealizaTransacaoStep step;

    public StepHandlerAdapter(RealizaTransacaoStep step, RealizaTransacaoHandler next) {
        super(next);
        this.step = step;
    }

    @Override
    protected RealizaTransacaoContext processar(RealizaTransacaoContext context) {
        return step.executar(context);
    }
}
