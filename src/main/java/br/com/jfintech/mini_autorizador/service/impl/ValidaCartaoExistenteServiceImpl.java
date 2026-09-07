package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;
import br.com.jfintech.mini_autorizador.exception.CartaoExistenteException;
import br.com.jfintech.mini_autorizador.repository.CartaoRepository;
import br.com.jfintech.mini_autorizador.service.ValidaCartaoExistenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidaCartaoExistenteServiceImpl implements ValidaCartaoExistenteService {

    private final CartaoRepository cartaoRepository;

    @Override
    public void validar(CartaoRequest request) {
        if (cartaoRepository.existsById(request.numeroCartao())) {
            throw new CartaoExistenteException("Cartão já existe", request);
        }
    }
}
