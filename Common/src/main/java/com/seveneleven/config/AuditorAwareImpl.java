package com.seveneleven.config;

import com.seveneleven.util.security.dto.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    // 현재 사용자의 ID나 이름을 반환
    public Optional<Long> getCurrentAuditor() {
        // SecurityContext에서 Authentication 객체를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나 인증되지 않은 경우 빈 Optional 반환
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }

        // principal이 CustomUserDetails라면 memberId를 반환
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return Optional.ofNullable(userDetails.getId());
        }

        // 예상하지 못한 principal 타입인 경우 빈 Optional 반환
        return Optional.empty();
    }
}