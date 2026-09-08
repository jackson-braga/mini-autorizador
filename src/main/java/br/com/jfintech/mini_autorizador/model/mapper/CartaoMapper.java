package br.com.jfintech.mini_autorizador.model.mapper;

import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;
import br.com.jfintech.mini_autorizador.controller.v1.response.CartaoCadastradoResponse;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CartaoMapper {

    @Mapping(target = "saldo", source = "saldo")
    @Mapping(target = "versao", ignore = true)
    @Mapping(target = "dataHoraCadastro", ignore = true)
    CartaoEntity toEntity(CartaoRequest request, BigDecimal saldo);

    CartaoCadastradoResponse toCadastradoResponse(CartaoEntity entity);
}
