package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.exception.NotFoundException;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import br.com.jfintech.mini_autorizador.repository.CartaoRepository;
import br.com.jfintech.mini_autorizador.service.CartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService {

    private final CartaoRepository cartaoRepository;

    @Override
    public BigDecimal obterSaldo(String numeroCartao) {
        return cartaoRepository.findById(numeroCartao)
                .map(CartaoEntity::getSaldo)
                .orElseThrow(() -> new NotFoundException("Cartão não encontrado"));
    }
}
