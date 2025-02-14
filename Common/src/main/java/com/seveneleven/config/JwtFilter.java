package com.seveneleven.config;

import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.security.dto.Token;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * JWT 필터 클래스
 * <br>
 * - HTTP 요청에서 JWT 토큰을 추출하고, 해당 토큰의 유효성을 검증합니다.
 * - 유효한 JWT 토큰이 있을 경우, 인증 정보를 Security Context에 저장합니다.
 */
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    public static final String ACCESS_HEADER  = "X-Access-Token";
    public static final String REFRESH_HEADER = "X-Refresh-Token";
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenProvider tokenProvider;
    private final ApplicationEventPublisher eventPublisher;

    public JwtFilter(CustomUserDetailsService customUserDetailsService, TokenProvider tokenProvider, ApplicationEventPublisher eventPublisher) {
        this.customUserDetailsService = customUserDetailsService;
        this.tokenProvider = tokenProvider;
        this.eventPublisher = eventPublisher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        //Prometheus 요청일 경우 로그 출력 안 함
        if (requestUri.startsWith("/actuator/prometheus")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Request Come : {} {}", request.getMethod(), request.getRequestURI());
        log.info("Request ContentType: {}", request.getContentType());

        String requestURI = request.getRequestURI(); // 요청 경로 확인

        // Refresh 요청은 필터를 통과시키도록 설정
        if ("/api/auth/refresh".equals(requestURI)) {
            log.info("[재발급 요청] jwt filter에서 다음 filter로 넘어가는 중 ... ");
            filterChain.doFilter(request, response); // Refresh 요청은 그대로 통과
            return ;
        }

        // 쿠키에서 토큰 가져오기
        Token token = new Token();

        if (Objects.nonNull(request.getCookies())) {
            for (Cookie cookie : request.getCookies()) {
                if (ACCESS_HEADER.equals(cookie.getName())) {
                    token.setAccessToken(cookie.getValue());
                    log.info("[JWT Filter] Access Token : {}", cookie.getValue());
                } else if (REFRESH_HEADER.equals(cookie.getName())) {
                    token.setRefreshToken(cookie.getValue());
                    log.info("[JWT Filter] Refresh Token : {}", cookie.getValue());
                }
            }
        } else {
            log.warn("Request Cookie is null");
        }


        if(Objects.nonNull(token.getAccessToken())) {

            // Access Token이 유효할 때
            if(tokenProvider.validateToken(token.getAccessToken())) {
                setAuthentication(token.getAccessToken());
            }
            // Access Token이 만료되었지만, Refresh Token이 유효할 때
            else if(token.getRefreshToken() != null && tokenProvider.validateToken(token.getRefreshToken())) {
                handleBusinessException(response, new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN));
                log.error("Access Token Expired : {}", token.getAccessToken());
                // eventPublisher.publishEvent(new AuthenticationFailureBadCredentialsEvent(null, new UsernameNotFoundException("Access Token Expired")));
                return;
            }
            // Access Token과 Refresh Token 모두 만료되었을 때
            else {
                handleBusinessException(response, new BusinessException(ErrorCode.UNAUTHORIZED));
                log.error("User Not Found");
                // eventPublisher.publishEvent(new AuthenticationFailureBadCredentialsEvent(null, new UsernameNotFoundException("User Not Found")));
                return;
            }
        }

        filterChain.doFilter(request, response); // 다음 필터로 넘기기
    }

    /**
     * Access Token으로 사용자 인증 설정
     *
     * @param token 유효한 Access Token
     */
    private void setAuthentication(String token) {
        String loginId = tokenProvider.getLoginId(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginId);

        if (userDetails != null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            eventPublisher.publishEvent(new AuthenticationSuccessEvent(authentication));
        }
    }

    // BusinessException 응답 처리 메서드
    private void handleBusinessException(HttpServletResponse response, BusinessException ex) throws IOException {
        response.setStatus(ex.getErrorCode().getStatus().value()); // HTTP 상태 코드 설정
        response.setContentType("application/json");
        response.getWriter().write("{\"code\": \"" + ex.getErrorCode().getCode() + "\", \"message\": \"" +  ex.getMessage() + "\", \"data\": \"\" }");
    }

}

