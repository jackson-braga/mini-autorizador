package br.com.jfintech.mini_autorizador.controller.v1;

import br.com.jfintech.mini_autorizador.config.SecurityConfig;
import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;
import br.com.jfintech.mini_autorizador.controller.v1.response.CartaoCadastradoResponse;
import br.com.jfintech.mini_autorizador.exception.ApplicationExceptionHandler;
import br.com.jfintech.mini_autorizador.exception.CartaoExistenteException;
import br.com.jfintech.mini_autorizador.exception.NotFoundException;
import br.com.jfintech.mini_autorizador.fixture.CartaoCadastradoResponseFixture;
import br.com.jfintech.mini_autorizador.fixture.CartaoRequestFixture;
import br.com.jfintech.mini_autorizador.service.CadastraCartaoService;
import br.com.jfintech.mini_autorizador.service.ConsultaCartaoService;
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

import java.util.stream.Stream;

import static br.com.jfintech.mini_autorizador.fixture.FixtureConstants.*;
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
    private CadastraCartaoService cadastraCartaoService;

    @MockitoBean
    private ConsultaCartaoService consultaCartaoService;

    @Test
    @DisplayName("deve criar cartão com 201 quando payload válido")
    void deveCriarCartaoCom201QuandoPayloadValido() throws Exception {
        CartaoRequest cartao = CartaoRequestFixture.criarCartaoRequestValido();
        CartaoCadastradoResponse esperado = CartaoCadastradoResponseFixture.criarCartaoCadastradoResponseValido();

        when(cadastraCartaoService.cadastrar(eq(cartao))).thenReturn(esperado);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartao)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(esperado)));

        verify(cadastraCartaoService).cadastrar(eq(cartao));
    }

    @Test
    @DisplayName("deve retornar 422 quando tentar criar cartão que já existe")
    void deveRetornar422QuandoCartaoJaExiste() throws Exception {
        CartaoRequest cartao = CartaoRequestFixture.criarCartaoRequestValido();

        when(cadastraCartaoService.cadastrar(eq(cartao))).thenThrow(new CartaoExistenteException("Cartão já existe", cartao));

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartao)))
                .andExpect(status().isUnprocessableContent())
                .andExpect(content().json(objectMapper.writeValueAsString(cartao)));

        verify(cadastraCartaoService).cadastrar(eq(cartao));
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

        verifyNoInteractions(cadastraCartaoService);
    }

    @Test
    @DisplayName("deve retornar 200 e o payload com saldo quando cartão existir")
    void deveRetornar200EBodyQuandoConsultaSaldo() throws Exception {

        when(consultaCartaoService.consultarSaldo(eq(NUMERO_CARTAO))).thenReturn(SALDO_INICIAL);

        mockMvc.perform(get(BASE_PATH + "/" + NUMERO_CARTAO))
                .andExpect(status().isOk())
                .andExpect(content().string(SALDO_INICIAL.toPlainString()));

        verify(consultaCartaoService).consultarSaldo(NUMERO_CARTAO);
    }

    @Test
    @DisplayName("deve retornar 404 quando cartão não existir na consulta de saldo")
    void deveRetornar404QuandoNaoExistirCartao() throws Exception {

        when(consultaCartaoService.consultarSaldo(eq(NUMERO_CARTAO))).thenThrow(new NotFoundException("Cartão não encontrado"));

        mockMvc.perform(get(BASE_PATH + "/" + NUMERO_CARTAO))
                .andExpect(status().isNotFound());

        verify(consultaCartaoService).consultarSaldo(NUMERO_CARTAO);
    }

    @ParameterizedTest
    @MethodSource("providerConsulaSaodoNumeroCartaoInvalidos")
    @DisplayName("deve retornar 400 quando numeroCartao da consulta de saldo for inválido")
    void deveRetornar400QuandoNumeroCartaoDaConsultaForInvalido(String numeroCartaoInvalido, String mensagemErro) throws Exception {

        mockMvc.perform(get(BASE_PATH + "/" + numeroCartaoInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(mensagemErro)));

        verifyNoInteractions(consultaCartaoService);
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

    private static Stream<Arguments> providerConsulaSaodoNumeroCartaoInvalidos() {
        return Stream.of(
                Arguments.of(NUMERO_CARTAO_INVALIDO, "numeroCartao deve conter 16 dígitos numéricos"),
                Arguments.of(NUMERO_CARTAO + "7", "numeroCartao deve conter 16 dígitos numéricos")
        );
    }
}
