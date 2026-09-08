package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.service.RealizaTransacaoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RealizaTransacaoServiceImpl implements RealizaTransacaoService {

    private final RealizaTransacaoChain realizaTransacaoChain;

    @Override
    public String realizarTransacao(TransacaoRequest request) {
        return realizaTransacaoChain.processar(request);
    }
}
