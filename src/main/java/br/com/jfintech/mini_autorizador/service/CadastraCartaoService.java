package br.com.jfintech.mini_autorizador.service;

import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;
import br.com.jfintech.mini_autorizador.controller.v1.response.CartaoCadastradoResponse;

public interface CadastraCartaoService {
    CartaoCadastradoResponse cadastrar(CartaoRequest request);
}
