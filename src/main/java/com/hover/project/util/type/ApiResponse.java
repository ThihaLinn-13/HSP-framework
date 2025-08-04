package com.hover.project.util.type;

public record ApiResponse<T>(
        long statusCode,
        String message,
        T data
) {



}
