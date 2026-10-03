package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.model.mapper.TransacaoMapper;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import br.com.jfintech.mini_autorizador.service.step.RealizaTransacaoHandler;
import br.com.jfintech.mini_autorizador.service.step.StepHandlerAdapter;
import br.com.jfintech.mini_autorizador.service.step.RealizaTransacaoStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class RealizaTransacaoChain {

    private static final String OK = "OK";

    private final List<RealizaTransacaoStep> steps;
    private final TransacaoMapper mapper;

    public String processar(TransacaoRequest request) {
        RealizaTransacaoContext context = mapper.toContext(request, BigDecimal.ZERO);

        RealizaTransacaoHandler chain = buildHandlerChain();
        chain.executar(context);

        return OK;
    }

    private RealizaTransacaoHandler buildHandlerChain() {
        if (steps.isEmpty()) {
            return new RealizaTransacaoHandler(null) {
                @Override
                protected RealizaTransacaoContext processar(RealizaTransacaoContext context) {
                    return context;
                }
            };
        }

        AtomicReference<RealizaTransacaoHandler> chain = new AtomicReference<>();
        steps.reversed()
                .forEach(step -> {
                    chain.set(new StepHandlerAdapter(step, chain.get()));
                });

        return chain.get();
    }
}
