package br.com.jfintech.mini_autorizador.controller.v1;

import br.com.jfintech.mini_autorizador.config.SecurityConfig;
import br.com.jfintech.mini_autorizador.controller.v1.request.TransacaoRequest;
import br.com.jfintech.mini_autorizador.exception.ApplicationExceptionHandler;
import br.com.jfintech.mini_autorizador.exception.SaldoInvalidoException;
import br.com.jfintech.mini_autorizador.fixture.TransacaoRequestFixture;
import br.com.jfintech.mini_autorizador.service.TransacaoService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransacaoController.class)
@Import({ApplicationExceptionHandler.class, ObjectMapper.class, SecurityConfig.class})
class TransacaoControllerTest {

    private static final String BASE_PATH = "/transacoes";
    private static final String OK = "OK";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransacaoService transacaoService;

    @Test
    @DisplayName("deve retornar 201 e OK quando transação autorizada")
    void deveRetornar201QuandoTransacaoAutorizada() throws Exception {
        var transacao = TransacaoRequestFixture.criarTransacaoRequestValida();

        when(transacaoService.realizarTransacao(eq(transacao))).thenReturn(OK);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isCreated())
                .andExpect(content().string(OK));

        verify(transacaoService).realizarTransacao(eq(transacao));
    }

    @Test
    @DisplayName("deve retornar 422 e SALDO_INSUFICIENTE quando saldo insuficiente")
    void deveRetornar422QuandoSaldoInsuficiente() throws Exception {
        var transacao = TransacaoRequestFixture.criarTransacaoRequestSaldoInsuficiente();

        when(transacaoService.realizarTransacao(eq(transacao))).thenThrow(new SaldoInvalidoException("Saldo insuficiente", "SALDO_INSUFICIENTE"));

        // then
        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isUnprocessableContent())
                .andExpect(content().string("SALDO_INSUFICIENTE"));

        verify(transacaoService).realizarTransacao(eq(transacao));
    }

    @Test
    @DisplayName("deve retornar 422 e SENHA_INVALIDA quando senha estiver errada")
    void deveRetornar422QuandoSenhaInvalida() throws Exception {
        var transacao = TransacaoRequestFixture.criarTransacaoRequestComSenhaInvalida();

        when(transacaoService.realizarTransacao(eq(transacao))).thenThrow(new SaldoInvalidoException("Senha inválida", "SENHA_INVALIDA"));

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isUnprocessableContent())
                .andExpect(content().string("SENHA_INVALIDA"));

        verify(transacaoService).realizarTransacao(eq(transacao));
    }

    @Test
    @DisplayName("deve retornar 422 e CARTAO_INEXISTENTE quando cartão não existir")
    void deveRetornar422QuandoCartaoNaoExistir() throws Exception {
        var transacao = TransacaoRequestFixture.criarTransacaoRequestComCartaoInexistente();

        when(transacaoService.realizarTransacao(eq(transacao))).thenThrow(new SaldoInvalidoException("Cartão inexistente", "CARTAO_INEXISTENTE"));

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isUnprocessableContent())
                .andExpect(content().string("CARTAO_INEXISTENTE"));

        verify(transacaoService).realizarTransacao(eq(transacao));
    }

    @ParameterizedTest
    @MethodSource("providerTransacaoPayloadsInvalidos")
    @DisplayName("deve retornar 400 quando payload de transação for inválido")
    void deveRetornar400QuandoPayloadInvalido(TransacaoRequest transacao, String mensagemErro) throws Exception {

        var request = post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(mensagemErro)));

        verify(transacaoService, never()).realizarTransacao(eq(transacao));
    }

    public static Stream<Arguments> providerTransacaoPayloadsInvalidos() {
        return Stream.of(
                Arguments.of(TransacaoRequestFixture.criarTransacaoRequestComNumeroCartaoInvalido(), "numeroCartao: numeroCartao deve conter 16 dígitos numéricos"),
                Arguments.of(TransacaoRequestFixture.criarTransacaoRequestComNumeroCartaoNulo(), "numeroCartao: numeroCartao não pode ser nulo ou vazio"),
                Arguments.of(TransacaoRequestFixture.criarTransacaoRequestComNumeroCartaoVazio(), "numeroCartao: numeroCartao não pode ser nulo ou vazio"),
                Arguments.of(TransacaoRequestFixture.criarTransacaoRequestComSenhaIncompleta(), "senhaCartao: senhaCartao deve conter 4 dígitos numéricos"),
                Arguments.of(TransacaoRequestFixture.criarTransacaoRequestComSenhaNulo(), "senhaCartao: senhaCartao não pode ser nula ou vazia"),
                Arguments.of(TransacaoRequestFixture.criarTransacaoRequestComSenhaVazio(), "senhaCartao: senhaCartao não pode ser nula ou vazia"),
                Arguments.of(TransacaoRequestFixture.criarTransacaoRequestComValorNegativo(), "valor: valor deve ser maior que zero"),
                Arguments.of(TransacaoRequestFixture.criarTransacaoRequestComValorNulo(), "valor: valor não pode ser nulo")
        );
    }
}
