package com.hover.project.util.type;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        String error,
        String errorDetail,
        int status,
        LocalDateTime timeStamp
) {
    public ErrorResponse(String message, String error,String errorDetail, int status) {
        this(message, error,errorDetail, status, LocalDateTime.now());
    }
}
