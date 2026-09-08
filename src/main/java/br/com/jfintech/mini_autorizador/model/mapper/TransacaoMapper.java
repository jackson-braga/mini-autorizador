package br.com.jfintech.mini_autorizador.model.mapper;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import br.com.jfintech.mini_autorizador.model.entity.TransacaoEntity;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TransacaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cartao", source = "cartao")
    @Mapping(target = "valor", source = "request.valor")
    @Mapping(target = "dataHoraTransacao", ignore = true)
    TransacaoEntity toEntity(TransacaoRequest request, CartaoEntity cartao);

    @Mapping(target = "cartao", ignore = true)
    @Mapping(target = "novoSaldo", source = "novoSaldo")
    RealizaTransacaoContext toContext(TransacaoRequest request, BigDecimal novoSaldo);
}
