package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import br.com.jfintech.mini_autorizador.repository.CartaoRepository;
import br.com.jfintech.mini_autorizador.service.PersisteCartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersisteCartaoServiceImpl implements PersisteCartaoService {

    private final CartaoRepository cartaoRepository;

    @Override
    public CartaoEntity persistir(CartaoEntity cartaoEntity) {
        return cartaoRepository.save(cartaoEntity);
    }
}
