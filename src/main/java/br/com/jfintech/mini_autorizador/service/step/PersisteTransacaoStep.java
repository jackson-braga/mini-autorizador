package br.com.jfintech.mini_autorizador.service.step;

import br.com.jfintech.mini_autorizador.model.entity.TransacaoEntity;
import br.com.jfintech.mini_autorizador.model.mapper.TransacaoMapper;
import br.com.jfintech.mini_autorizador.repository.TransacaoRepository;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(60)
@RequiredArgsConstructor
public class PersisteTransacaoStep implements RealizaTransacaoStep {

    private final TransacaoRepository transacaoRepository;
    private final TransacaoMapper transacaoMapper;

    @Override
    public void executar(RealizaTransacaoContext context) {
        TransacaoEntity transacao = transacaoMapper.toEntity(context.getRequest(), context.getCartao());
        transacaoRepository.save(transacao);
    }
}
