package br.com.jfintech.mini_autorizador.controller.v1;

import br.com.jfintech.mini_autorizador.config.SecurityConfig;
import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;
import br.com.jfintech.mini_autorizador.exception.ApplicationExceptionHandler;
import br.com.jfintech.mini_autorizador.exception.CartaoExistenteException;
import br.com.jfintech.mini_autorizador.exception.NotFoundException;
import br.com.jfintech.mini_autorizador.fixture.CartaoRequestFixture;
import br.com.jfintech.mini_autorizador.fixture.CartaoResponseFixture;
import br.com.jfintech.mini_autorizador.service.CartaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartaoController.class)
@Import({ApplicationExceptionHandler.class, ObjectMapper.class, SecurityConfig.class})
class CartaoControllerTest {

    private static final String BASE_PATH = "/cartoes";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CartaoService cartaoService;

    @Test
    @DisplayName("deve criar cartão com 201 quando payload válido")
    void deveCriarCartaoCom201QuandoPayloadValido() throws Exception {
        var cartao = CartaoRequestFixture.criarCartaoRequestValido();
        var esperado = CartaoResponseFixture.criarCartaoResponseValido();

        when(cartaoService.criarCartao(eq(cartao))).thenReturn(esperado);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartao)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(esperado)));

        verify(cartaoService).criarCartao(eq(cartao));
    }

    @Test
    @DisplayName("deve retornar 422 quando tentar criar cartão que já existe")
    void deveRetornar422QuandoCartaoJaExiste() throws Exception {
        var cartao = CartaoRequestFixture.criarCartaoRequestValido();
        var esperado = CartaoRequestFixture.criarCartaoRequestValido();

        when(cartaoService.criarCartao(eq(cartao))).thenThrow(new CartaoExistenteException("Cartão já existe", cartao));

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartao)))
                .andExpect(status().isUnprocessableContent())
                .andExpect(content().json(objectMapper.writeValueAsString(esperado)));

        verify(cartaoService).criarCartao(eq(cartao));
    }

    @ParameterizedTest
    @MethodSource("providerCriarCartaoPayloadsInvalidos")
    @DisplayName("deve retornar 400 quando payload de criação for inválido")
    void deveRetornar400QuandoPayloadInvalido(CartaoRequest cartao, String mensagemErro) throws Exception {

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartao)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(mensagemErro)));

        verifyNoInteractions(cartaoService);
    }

    @Test
    @DisplayName("deve retornar 200 e o payload com saldo quando cartão existir")
    void deveRetornar200EBodyQuandoConsultaSaldo() throws Exception {
        String numero = CartaoRequestFixture.NUMERO_CARTAO;
        BigDecimal saldoEsperado = BigDecimal.valueOf(500.00);

        when(cartaoService.obterSaldo(eq(numero))).thenReturn(saldoEsperado);

        mockMvc.perform(get(BASE_PATH + "/" + numero))
                .andExpect(status().isOk())
                .andExpect(content().string(saldoEsperado.toPlainString()));

        verify(cartaoService).obterSaldo(numero);
    }

    @Test
    @DisplayName("deve retornar 404 quando cartão não existir na consulta de saldo")
    void deveRetornar404QuandoCartaoNaoExistir() throws Exception {
        String numero = "0000111122223333";

        when(cartaoService.obterSaldo(eq(numero))).thenThrow(new NotFoundException("Cartão não encontrado"));

        // then
        mockMvc.perform(get(BASE_PATH + "/" + numero))
                .andExpect(status().isNotFound());

        verify(cartaoService).obterSaldo(numero);
    }

    private static Stream<Arguments> providerCriarCartaoPayloadsInvalidos() {
        return Stream.of(
                Arguments.of(CartaoRequestFixture.criarCartaoRequestNumeroNula(), "numeroCartao não pode ser nulo ou vazio"),
                Arguments.of(CartaoRequestFixture.criarCartaoRequestNumeroVazio(), "numeroCartao não pode ser nulo ou vazio"),
                Arguments.of(CartaoRequestFixture.criarCartaoRequestNumeroInvalido(), "numeroCartao deve conter 16 dígitos numéricos"),
                Arguments.of(CartaoRequestFixture.criarCartaoRequestSenhaNula(), "senha não pode ser nula ou vazia"),
                Arguments.of(CartaoRequestFixture.criarCartaoRequestSenhaVazia(), "senha não pode ser nula ou vazia"),
                Arguments.of(CartaoRequestFixture.criarCartaoRequesSenhaInvalido(), "senha deve conter 4 dígitos numéricos")
        );
    }

}
