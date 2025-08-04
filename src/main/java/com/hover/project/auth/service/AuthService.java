package com.hover.project.auth.service;

import com.hover.project.auth.dto.AuthRequestDto;
import com.hover.project.auth.dto.TokenResponseDto;


public interface AuthService   {

    public TokenResponseDto login(AuthRequestDto authRequestDto);

    public TokenResponseDto refreshToken(TokenResponseDto tokenResponseDto);

    public void createDemoUser();

}
