package com.kayo.libraryapi.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.net.ssl.HttpsURLConnection;
import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AutorNotFound.class)
    public ResponseEntity<StandardError> AutorNotFound(AutorNotFound ex, HttpServletRequest request, MethodArgumentNotValidException mEx) {

        List<FieldError> fieldErrors = mEx.getFieldErrors();
        List<ErrorCampo> errorCampo = fieldErrors.stream().map(fe -> new ErrorCampo(fe.getField(), fe.getDefaultMessage())).toList();

        StandardError error = new StandardError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Autor not found",
                ex.getMessage(),
                request.getRequestURI(),
                errorCampo
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> validationError(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ErrorCampo> errorCampo = ex.getFieldErrors()
                .stream()
                .map(fe -> new ErrorCampo(
                        fe.getField(),
                        fe.getDefaultMessage()
                ))
                .toList();

        StandardError error = new StandardError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                "Erro de validação",
                request.getRequestURI(),
                errorCampo
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<StandardError> handleAccesDenielException(AccessDeniedException ex, HttpServletRequest request) {
        StandardError standardError = new StandardError(
                Instant.now(),
                HttpStatus.FORBIDDEN.value(),
                "Acesso negado",
                ex.getMessage(),
                request.getRequestURI(),
                null

        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(standardError);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> HandleErrosNãoTratados(
            RuntimeException ex, HttpServletRequest request
    ) {
        StandardError standardError = new StandardError(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado. Entre em contato com a admininstração.",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardError);
    }


}