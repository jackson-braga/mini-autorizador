package br.com.jfintech.mini_autorizador.controller.v1;

import br.com.jfintech.mini_autorizador.controller.v1.request.CartaoRequest;
import br.com.jfintech.mini_autorizador.controller.v1.response.CartaoCadastradoResponse;
import br.com.jfintech.mini_autorizador.service.CadastraCartaoService;
import br.com.jfintech.mini_autorizador.service.ConsultaCartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
@Tag(name = "Cartões", description = "Operações relacionadas a cartões")
public class CartaoController {

    private final CadastraCartaoService cadastraCartaoService;
    private final ConsultaCartaoService consultaCartaoService;

    @Operation(summary = "Cria novo cartão")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CartaoCadastradoResponse.class))),
            @ApiResponse(responseCode = "422", description = "Cartão já existe", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CartaoCadastradoResponse> criarCartao(@Valid @RequestBody CartaoRequest request) {

        CartaoCadastradoResponse cartao = cadastraCartaoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartao);
    }

    @Operation(summary = "Consulta saldo do cartão", description = "Retorna o saldo disponível do cartão informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saldo retornado com sucesso",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Cartão não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> obterSaldo(@PathVariable String numeroCartao) {

        BigDecimal saldo = consultaCartaoService.consultarSaldo(numeroCartao);
        return ResponseEntity.ok(saldo);
    }
}
