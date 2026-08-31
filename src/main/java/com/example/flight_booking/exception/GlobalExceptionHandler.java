package com.example.flight_booking.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResponseStatusException.class)
    @SuppressWarnings("unused") // uyarıyı durdurmaya yarar. Sanki arka planda çağırılmadığı için hata veriliyormuş gibi olur ama arka planda çalışan bir yapıdır ve bunu spring boot farkedemez.
    public ResponseEntity<Map<String, String>> handleResponseStatusException(
            ResponseStatusException e) {

        String message = e.getReason() != null
                ? e.getReason()
                : "Request could not be processed.";

        logger.warn("istek hatası. Durum: {}, Mesaj: {}", e.getStatusCode(), e.getReason());

        return ResponseEntity
                .status(e.getStatusCode())
                .body(Map.of("message", message));
    }

    @ExceptionHandler(Exception.class)
    @SuppressWarnings("unused")
    public ResponseEntity<Map<String, String>> handleUnexpectedException(Exception e) {
        logger.error("Beklenmeyen bir hata oluştu.", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Sunucuda beklenmeyen bir hata oluştu."));
    }
}
