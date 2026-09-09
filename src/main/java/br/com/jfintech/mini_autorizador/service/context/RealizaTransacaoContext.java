package br.com.jfintech.mini_autorizador.service.context;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import lombok.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RealizaTransacaoContext {

    private TransacaoRequest request;
    private CartaoEntity cartao;
    private BigDecimal novoSaldo;
}
