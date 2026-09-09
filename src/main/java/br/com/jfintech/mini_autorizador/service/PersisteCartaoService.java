package br.com.jfintech.mini_autorizador.service;

import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;

public interface PersisteCartaoService {
    CartaoEntity persistir(CartaoEntity cartaoEntity);
}
