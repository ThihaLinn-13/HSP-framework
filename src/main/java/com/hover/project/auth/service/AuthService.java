package com.hover.project.auth.service;

import com.hover.project.auth.dto.AuthRequestDto;
import com.hover.project.auth.dto.TokenResponseDto;
import com.hover.project.util.type.ApiResponse;

public interface AuthService {

    public TokenResponseDto login(AuthRequestDto authRequestDto);

    public ApiResponse<TokenResponseDto> refreshToken(TokenResponseDto tokenResponseDto);

    public void createDemoUser();

}
