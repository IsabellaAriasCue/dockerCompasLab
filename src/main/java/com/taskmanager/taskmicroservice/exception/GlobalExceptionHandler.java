package com.taskmanager.taskmicroservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la aplicación.
 * Centraliza el manejo de errores (SRP) y proporciona respuestas consistentes.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";

    /**
     * Maneja excepciones de recursos no encontrados.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Recurso no encontrado: {}", ex.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(TIMESTAMP, LocalDateTime.now());
        errorResponse.put(STATUS, HttpStatus.NOT_FOUND.value());
        errorResponse.put(ERROR, "Not Found");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja errores de validación de datos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Error de validación: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(TIMESTAMP, LocalDateTime.now());
        errorResponse.put(STATUS, HttpStatus.BAD_REQUEST.value());
        errorResponse.put(ERROR, "Validation Failed");
        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja cualquier otra excepción no contemplada.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        log.error("Error inesperado: ", ex);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(TIMESTAMP, LocalDateTime.now());
        errorResponse.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put(ERROR, "Internal Server Error");
        errorResponse.put("message", "Ocurrió un error inesperado. Por favor, contacte al administrador.");

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}