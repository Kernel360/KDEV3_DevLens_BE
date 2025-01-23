package com.seveneleven.config;

import com.seveneleven.entity.member.RefreshToken;
import com.seveneleven.util.security.dto.CustomUserDetails;
import com.seveneleven.util.security.dto.TokenResponse;
import com.seveneleven.util.security.repository.RefreshTokenRepositoryImpl;
import com.seveneleven.util.security.service.RefreshTokenServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
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
    private final RefreshTokenRepositoryImpl refreshTokenRepositoryImpl;
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
            @Value("${jwt.refresh-token-validity}") long refreshTokenValidityInSeconds, RefreshTokenRepositoryImpl refreshTokenRepositoryImpl) {
        this.secret = secret;
        this.ACCESS_TOKEN_EXPIRE_TIME = acessTokenValidityInSeconds * 1000;
        this.REFRESH_TOKEN_EXPIRE_TIME = refreshTokenValidityInSeconds * 1000;
        this.refreshTokenRepositoryImpl = refreshTokenRepositoryImpl;
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

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.ACCESS_TOKEN_EXPIRE_TIME);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((String) userDetails.getId()) // 회원 ID(PK)를 Subject로 설정
                .claim("loginId", userDetails.getLoginId()) // 로그인 ID 추가
                .claim("email", userDetails.getEmail()) // 이메일 추가
                .claim(AUTHORITIES_KEY, authorities) // 권한 추가
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
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
        refreshTokenRepositoryImpl.save(
                new RefreshToken(refreshToken, userDetails.getId()),
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
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.REFRESH_TOKEN_EXPIRE_TIME); // Refresh Token 만료 시간 설정

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((String) userDetails.getId()) // 회원 ID(PK)를 Subject로 설정
                .signWith(key, SignatureAlgorithm.HS512) // 동일한 키 사용
                .setExpiration(validity) // 만료 시간 설정
                .compact();
    }


    public String generateTokenDto(Authentication authentication) {
        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "USER"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 151621022 (ex)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return "";
//        return TokenDto.builder()
//                .grantType(BEARER_TYPE)
//                .accessToken(accessToken)
//                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
//                .refreshToken(refreshToken)
//                .build();
    }


    /*
    * 함수명 : getExpirationFromToken
    * JWT 만료 시간 추출
    *
    * @param token JWT 토큰    
    * @return 만료 시간 반환
    * */
    public LocalDateTime getExpirationFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        Date expiration = claims.getExpiration();
        return expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * JWT 토큰을 파싱하여 Authentication 객체를 생성합니다.
     *
     * @param token JWT 토큰
     * @return Authentication 객체
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * Token에서 User ID 추출
     * @param token
     * @return User ID
     */
    public String getLoginId(String token) {
        return parseClaims(token).get("loginId", String.class);
    }

    /**
     * JWT Claims 추출
     * @param accessToken
     * @return JWT Claims
     */
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
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
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}

