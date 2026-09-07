package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.exception.NotFoundException;
import br.com.jfintech.mini_autorizador.repository.CartaoRepository;
import br.com.jfintech.mini_autorizador.service.ConsultaCartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ConsultaCartaoServiceImpl implements ConsultaCartaoService {

    private final CartaoRepository cartaoRepository;

    @Override
    public BigDecimal consultarSaldo(String numeroCartao) {
        return cartaoRepository.findSaldoByNumeroCartao(numeroCartao)
                .orElseThrow(() -> new NotFoundException("Cartão não encontrado"));
    }
}
