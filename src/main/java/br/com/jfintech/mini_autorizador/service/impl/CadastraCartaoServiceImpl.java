package br.com.jfintech.mini_autorizador.service.impl;

import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;
import br.com.jfintech.mini_autorizador.controller.v1.response.CartaoCadastradoResponse;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import br.com.jfintech.mini_autorizador.model.mapper.CartaoMapper;
import br.com.jfintech.mini_autorizador.service.CadastraCartaoService;
import br.com.jfintech.mini_autorizador.service.PersisteCartaoService;
import br.com.jfintech.mini_autorizador.service.ValidaCartaoExistenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CadastraCartaoServiceImpl implements CadastraCartaoService {

    private static final BigDecimal SALDO_INICIAL = new BigDecimal("500.00");

    private final ValidaCartaoExistenteService validaCartaoExistenteService;
    private final CartaoMapper cartaoMapper;
    private final PersisteCartaoService persisteCartaoService;

    @Override
    public CartaoCadastradoResponse cadastrar(CartaoRequest request) {
        validaCartaoExistenteService.validar(request);

        CartaoEntity novoCartao = cartaoMapper.toEntity(request, SALDO_INICIAL);

        CartaoEntity cartaoSalvo = persisteCartaoService.persistir(novoCartao);
        return cartaoMapper.toCadastradoResponse(cartaoSalvo);
    }
}
