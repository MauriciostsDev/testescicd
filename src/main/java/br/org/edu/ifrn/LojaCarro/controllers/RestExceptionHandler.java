package br.org.edu.ifrn.LojaCarro.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(CarroInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleCarroInvalido(CarroInvalidoException ex) {
        logger.warn("Tratando exceção de negócio: CARRO_INVALIDO - {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse("CARRO_INVALIDO", ex.getMessage()));
    }

    public static record ErrorResponse(String error, String detail) {
    }
}
