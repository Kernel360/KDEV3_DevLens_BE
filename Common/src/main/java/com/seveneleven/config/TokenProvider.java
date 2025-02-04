package com.seveneleven.config;

import com.seveneleven.entity.member.RefreshToken;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.security.dto.CustomUserDetails;
import com.seveneleven.util.security.dto.TokenResponse;
import com.seveneleven.util.security.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * JWT 토큰의 생성, 인증, 유효성 검증 등을 담당하는 클래스
 */
@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";
    private final long ACCESS_TOKEN_EXPIRE_TIME;
    private final long REFRESH_TOKEN_EXPIRE_TIME;
    private final RefreshTokenRepository refreshTokenRepository;
    private final String secret;
    private Key key;

    /**
     * 생성자: JWT 토큰의 비밀키와 유효 시간을 초기화합니다.
     *
     * @param secret              Base64로 인코딩된 JWT 비밀키
     * @param acessTokenValidityInSeconds JWT Access 토큰의 유효 기간 (초 단위)
     * @param refreshTokenValidityInSeconds JWT Refresh 토큰의 유효 기간 (초 단위)
     */
    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity}") long acessTokenValidityInSeconds,
            @Value("${jwt.refresh-token-validity}") long refreshTokenValidityInSeconds,
            RefreshTokenRepository refreshTokenRepository) {
        this.secret = secret;
        this.ACCESS_TOKEN_EXPIRE_TIME = acessTokenValidityInSeconds * 1000;
        this.REFRESH_TOKEN_EXPIRE_TIME = refreshTokenValidityInSeconds * 1000;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /**
     * Bean이 초기화된 후 Base64로 인코딩된 secret 값을 디코딩하여 Key 객체를 생성합니다.
     */
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 인증 정보를 기반으로 JWT 토큰을 생성합니다.
     *
     * @param authentication Spring Security의 Authentication 객체
     * @return 생성된 JWT 토큰
     */
    public String createAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Instant now = Instant.now();
        Instant expirationTime = now.plusMillis(this.ACCESS_TOKEN_EXPIRE_TIME);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .claim("memberId", userDetails.getId()) // 회원 ID(PK) 추가
                .claim("loginId", userDetails.getLoginId()) // 로그인 ID 추가
                .claim("name", userDetails.getUsername()) // 이름 추가
                .claim(AUTHORITIES_KEY, authorities) // 권한 추가
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(Date.from(now))              // 발급 시간
                .setExpiration(Date.from(expirationTime)) // 만료 시간
                .compact();
    }

    /**
     * 인증 정보를 기반으로 Access Token과 Refresh Token을 생성합니다.
     *
     * @param authentication Spring Security의 Authentication 객체
     * @return Access Token과 Refresh Token을 포함한 DTO
     */
    public TokenResponse createTokens(Authentication authentication) {
        String accessToken = createAccessToken(authentication);
        String refreshToken = createRefreshToken(authentication);

        // Refresh Token은 Redis에 저장
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        refreshTokenRepository.save(
                new RefreshToken(refreshToken, userDetails.getId().toString()),
                REFRESH_TOKEN_EXPIRE_TIME
        );

        return new TokenResponse(accessToken, refreshToken);
    }

    /**
     * 인증 정보를 기반으로 Refresh Token을 생성합니다.
     *
     * @param authentication Spring Security의 Authentication 객체
     * @return 생성된 Refresh Token
     */
    public String createRefreshToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant expirationTime = now.plusMillis(this.REFRESH_TOKEN_EXPIRE_TIME);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .claim("memberId", userDetails.getId()) // 회원 ID(PK) 추가
                .claim("loginId", userDetails.getLoginId()) // 로그인 ID 추가
                .signWith(key, SignatureAlgorithm.HS512) // 동일한 키 사용
                .setIssuedAt(Date.from(now))              // 발급 시간
                .setExpiration(Date.from(expirationTime)) // 만료 시간
                .compact();
    }

    /**
     * Access Token 만료 시간을 반환합니다.
     *
     * @return Access Token 만료 시간 (밀리초 단위)
     */
    public long getAccessTokenExpireTime() {
        return ACCESS_TOKEN_EXPIRE_TIME;
    }

    /**
     * Refresh Token 만료 시간을 반환합니다.
     *
     * @return Refresh Token 만료 시간 (밀리초 단위)
     */
    public long getRefreshTokenExpireTime() {
        return REFRESH_TOKEN_EXPIRE_TIME;
    }


    /**
     * Token에서 login ID 추출
     * @param token
     * @return login ID
     */
    public String getLoginId(String token) {
        return parseClaims(token).get("loginId", String.class);
    }

    /**
     * Token에서 Member ID 추출
     * @param token
     * @return Member ID
     */
    public String getMemberId(String token) {
        return parseClaims(token).get("memberId", String.class);
    }

    /**
     * JWT Claims 추출
     * @param token
     * @return JWT Claims
     */
    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * JWT 토큰의 유효성을 검증합니다.
     *
     * @param token 검증할 JWT 토큰
     * @return 토큰이 유효하면 true, 그렇지 않으면 false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
            throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN);
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
            throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN);
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
            throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
        return false;
    }
}

