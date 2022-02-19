package com.stx.config;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger LOGGER = Logger.getLogger(getClass());

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Error<String>> handleValidationException(HttpServletRequest request, ValidationException e) {
        LOGGER.error("ValidationException " + request.getRequestURI(), e);
        return ResponseEntity
                .badRequest()
                .body(new Error<>("Validation exception", List.of(e.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error<String>> handleInternalServerError(HttpServletRequest request, Exception e) {
        LOGGER.error("handleInternalServerError " + request.getRequestURI(), e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error<>("Internal server error", List.of(e.getMessage())));
    }

    public record Error<T>(String message, List<T> details) {
        public String getMessage() {
            return message;
        }

        public List<T> getDetails() {
            return details;
        }
    }
}
