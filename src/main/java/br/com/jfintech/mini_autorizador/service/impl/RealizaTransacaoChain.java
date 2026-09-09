package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.model.mapper.TransacaoMapper;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import br.com.jfintech.mini_autorizador.service.step.RealizaTransacaoStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RealizaTransacaoChain {

    private static final String OK = "OK";

    private final List<RealizaTransacaoStep> steps;
    private final TransacaoMapper mapper;

    public String processar(TransacaoRequest request) {
        RealizaTransacaoContext context = mapper.toContext(request, BigDecimal.ZERO);
        context.setRequest(request);

        steps.forEach(step -> step.executar(context));

        return OK;
    }
}
