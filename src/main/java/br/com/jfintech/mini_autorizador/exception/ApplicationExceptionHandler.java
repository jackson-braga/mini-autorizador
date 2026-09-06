package br.com.jfintech.mini_autorizador.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public void handleNotFound() { }

    @ExceptionHandler(CartaoExistenteException.class)
    public ResponseEntity<Object> handleCartaoExistente(CartaoExistenteException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(ex.getPayload());
    }

    @ExceptionHandler(SaldoInvalidoException.class)
    public ResponseEntity<String> handleSaldoInvalido(SaldoInvalidoException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(ex.getPayload());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        Map<String,Object> body = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "error", "Business Error",
                "message", ex.getMessage(),
                "path", request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(message);
    }

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
//        return ResponseEntity.badRequest().body("Payload inválido");
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleAny(Exception ex, HttpServletRequest request) {
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
