package br.com.jfintech.mini_autorizador.model.mapper;

import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;
import br.com.jfintech.mini_autorizador.controller.v1.response.CartaoCadastradoResponse;
import br.com.jfintech.mini_autorizador.fixture.CartaoCadastradoResponseFixture;
import br.com.jfintech.mini_autorizador.fixture.CartaoEntityFixture;
import br.com.jfintech.mini_autorizador.fixture.CartaoRequestFixture;
import br.com.jfintech.mini_autorizador.model.entity.CartaoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.SALDO_INICIAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("CartaoMapper")
class CartaoMapperTest {

    private final CartaoMapper mapper = Mappers.getMapper(CartaoMapper.class);

    @Test
    @DisplayName("Deve converter CartaoRequest para CartaoEntity corretamente")
    void deveConverterCartaoRequestParaCartaoEntity() {
        CartaoRequest request = CartaoRequestFixture.criarCartaoRequestValido();
        CartaoEntity entityEsperado = CartaoEntityFixture.criarCartaoValido();

        CartaoEntity entity = mapper.toEntity(request, SALDO_INICIAL);

        assertEquals(entityEsperado, entity);
    }

    @Test
    @DisplayName("Deve converter CartaoEntity para CartaoCadastradoResponse corretamente")
    void deveConverterCartaoEntityParaCartaoCadastradoResponse() {
        CartaoEntity entity = CartaoEntityFixture.criarCartaoValido();
        CartaoCadastradoResponse responseEsperado = CartaoCadastradoResponseFixture.criarCartaoCadastradoResponseValido();

        CartaoCadastradoResponse response = mapper.toCadastradoResponse(entity);

        assertEquals(responseEsperado, response);
    }
}
