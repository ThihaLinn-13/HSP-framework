package com.hover.project.util.type;

import org.springframework.http.ResponseEntity;

public record ApiResponse<T>(
                int statusCode,
                String message,
                T data) {

        public ResponseEntity<ApiResponse<T>> buildResponse(ApiResponse<T> apiResponse) {
                return ResponseEntity.status(apiResponse.statusCode())
                                .body(apiResponse);
        }

        public ApiResponse(HttpStatusCode httpStatusCode, T data) {
                this(httpStatusCode.getCode(), httpStatusCode.getMessage(), data);
        }
        public ApiResponse(HttpStatusCode httpStatusCode,String message, T data) {
                this(httpStatusCode.getCode(), message, data);
        }

}
