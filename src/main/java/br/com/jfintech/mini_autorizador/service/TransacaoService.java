package br.com.jfintech.mini_autorizador.service;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;

public interface TransacaoService {
    String realizarTransacao(TransacaoRequest request);
}
