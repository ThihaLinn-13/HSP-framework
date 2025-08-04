package com.hover.project.auth.dto;

public record TokenResponseDto(
        String accessToken,
        String refreshToken
) {
}
