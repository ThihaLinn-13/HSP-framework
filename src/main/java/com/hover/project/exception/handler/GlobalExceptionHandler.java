package com.hover.project.exception.handler;

import com.hover.project.exception.util.ExceptionUtil;
import com.hover.project.util.type.ApiResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<String>> handleJwtException(JwtException ex) {

        ApiResponse<String> response = new ApiResponse<>(
                401,
                ex.getLocalizedMessage()+ExceptionUtil.getStackTraceAsString(ex),
                null
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        ApiResponse<String> response = new ApiResponse<>(
                401,
                ex.getLocalizedMessage()+ExceptionUtil.getStackTraceAsString(ex),
                null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
        ApiResponse<String> response = new ApiResponse<>(
                401,
                ex.getLocalizedMessage()+ExceptionUtil.getStackTraceAsString(ex),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
