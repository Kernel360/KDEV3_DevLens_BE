package com.seveneleven.util.security;

import com.seveneleven.util.security.dto.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;

public class GetRoleUtil {

    // 인스턴스 생성 방지
    private GetRoleUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 사용자 역할(Role) 가져오기
     * @param userDetails 인증된 사용자 정보
     * @return 역할 문자열 (예: "ADMIN", "USER")
     */
    public static String getUserRole(CustomUserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(auth -> auth.replace("ROLE_", "")) // "ROLE_" 접두사 제거
                .findFirst()
                .orElse("USER"); // 기본값 설정
    }
}