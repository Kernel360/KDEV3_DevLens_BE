package com.seveneleven.controller;

import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.dto.TokenResponse;
import com.seveneleven.util.security.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final RefreshTokenService refreshTokenService;

    /**
     * 함수명 : refreshAccessToken
     * Access Token 재발급 API
     *
     */
    @PostMapping("/refresh")
    public ResponseEntity<APIResponse<TokenResponse>> refreshAccessToken(@RequestHeader("X-Refresh-Token") String refreshToken) {

        TokenResponse tokens = refreshTokenService.refreshAccessToken(refreshToken);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, tokens));
    }
}
