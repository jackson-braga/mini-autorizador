package br.com.jfintech.mini_autorizador.model.mapper;

import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.fixture.CartaoEntityFixture;
import br.com.jfintech.mini_autorizador.fixture.RealizaTransacaoContextFixture;
import br.com.jfintech.mini_autorizador.fixture.TransacaoEntityFixture;
import br.com.jfintech.mini_autorizador.fixture.TransacaoRequestFixture;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import br.com.jfintech.mini_autorizador.model.entity.TransacaoEntity;
import br.com.jfintech.mini_autorizador.service.context.RealizaTransacaoContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("TransacaoMapper")
class TransacaoMapperTest {

    private final TransacaoMapper mapper = Mappers.getMapper(TransacaoMapper.class);

    @Test
    @DisplayName("Deve converter TransacaoRequest e CartaoEntity para TransacaoEntity corretamente")
    void deveConverterTransacaoRequestParaTransacaoEntity() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestValida();
        CartaoEntity cartao = CartaoEntityFixture.criarCartaoValido();

        TransacaoEntity entityEsperada = TransacaoEntityFixture.criarTransacaoValidaParaCartao(cartao);

        TransacaoEntity entity = mapper.toEntity(request, cartao);

        assertEquals(entityEsperada, entity);
    }

    @Test
    @DisplayName("Deve converter TransacaoRequest em RealizaTransacaoContext corretamente")
    void deveConverterTransacaoRequestParaContexto() {
        TransacaoRequest request = TransacaoRequestFixture.criarTransacaoRequestValida();
        RealizaTransacaoContext contextEsperado = RealizaTransacaoContextFixture.criarContextoComDados(request);

        RealizaTransacaoContext context = mapper.toContext(request, BigDecimal.ZERO);

        assertEquals(contextEsperado, context);
    }
}
