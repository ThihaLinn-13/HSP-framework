package com.hover.project.auth.controller;

import com.hover.project.auth.dto.AuthRequestDto;
import com.hover.project.auth.dto.TokenResponseDto;
import com.hover.project.auth.service.AuthService;
import com.hover.project.security.service.JwtService;
import com.hover.project.util.type.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponseDto>> login(@RequestBody AuthRequestDto authRequestDto) {
        //authService.createDemoUser();
        TokenResponseDto token = authService.login(authRequestDto);
        ApiResponse<TokenResponseDto> response = new ApiResponse<>(200,"Successfully Login",token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public TokenResponseDto refreshToken(@RequestBody TokenResponseDto tokenResponseDto) {

        return authService.refreshToken(tokenResponseDto);
    }


}
