package br.com.jfintech.mini_autorizador.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public void handleNotFound(NotFoundException ex) {
        log.error("Recurso não encontrado: {}", ex.getMessage(), ex);
    }

    @ExceptionHandler(CartaoExistenteException.class)
    public ResponseEntity<Object> handleCartaoExistente(CartaoExistenteException ex, HttpServletRequest request) {
        log.error("Cartão já existe: {}", ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(ex.getPayload());
    }

    @ExceptionHandler(TransacaoInvalidaException.class)
    public ResponseEntity<String> handleSaldoInvalido(TransacaoInvalidaException ex, HttpServletRequest request) {
        log.error("Saldo inválido: {}", ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(ex.getPayload());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        log.error("Erro de negócio: {}", ex.getMessage(), ex);

        Map<String,Object> body = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "error", "Business Error",
                "message", ex.getMessage(),
                "path", request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(body);
    }

    @ExceptionHandler({ObjectOptimisticLockingFailureException.class, PessimisticLockingFailureException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleConcurrentModification(Exception ex) {
        log.error("Conflito de concorrência ao atualizar cartão: {}", ex.getMessage(), ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        log.error("Argumentos inválidos: {}", exception.getMessage(), exception);

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException exception) {
        log.error("Violação de constraint: {}", exception.getMessage(), exception);

        String message = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleAny(Exception ex, HttpServletRequest request) {
        log.error("Erro inesperado: {}", ex.getMessage(), ex);

        Map<String,Object> body = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error", "Internal Server Error",
                "message", ex.getMessage(),
                "path", request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
