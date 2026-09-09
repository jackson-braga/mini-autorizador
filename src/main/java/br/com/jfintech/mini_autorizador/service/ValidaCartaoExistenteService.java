package br.com.jfintech.mini_autorizador.service;

import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;

public interface ValidaCartaoExistenteService {
    void validar(CartaoRequest request);
}
