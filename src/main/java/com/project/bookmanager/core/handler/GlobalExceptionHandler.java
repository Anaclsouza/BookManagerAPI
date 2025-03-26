package com.project.bookmanager.core.handler;
import com.project.bookmanager.core.errors.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    private Map<String, Object> extract(BaseException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", System.currentTimeMillis());

        if (ex.getMetadata() != null && !ex.getMetadata().isEmpty()) {
            body.put("metadata", ex.getMetadata());
        }

        return body;
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex) {
        switch (ex.getLogLevel()) {
            case ERROR -> log.error("Erro: {}", ex.getMessage());
            case WARN -> log.warn("Aviso: {}", ex.getMessage());
            case INFO -> log.info("Info: {}", ex.getMessage());
            case DEBUG -> log.debug("Debug: {}", ex.getMessage());
            default -> log.trace("Trace: {}", ex.getMessage());
        }

        Map<String, Object> body = extract(ex);

        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        log.error("Erro inesperado: {}", ex.getMessage());

        Map<String, Object> body = new HashMap<>();
        body.put("error", "INTERNAL_ERROR");
        body.put("message", "Erro inesperado ocorreu");

        return ResponseEntity.status(500).body(body);
    }
}
