package com.seveneleven.config;

import com.seveneleven.util.security.TokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Spring Security 설정을 정의하는 클래스
 *
 * - JWT 기반 인증을 사용하도록 구성합니다.
 * - CORS, CSRF, 인증 및 권한 관리, 세션 정책 등을 설정합니다.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig {

    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SpringSecurityConfig(TokenProvider tokenProvider, TokenRepository tokenRepository, CustomUserDetailsService customUserDetailsService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.tokenRepository = tokenRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/member/**", "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
            "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/actuator/**"
    };

    /**
     * Spring Security 필터 체인을 구성합니다.
     *
     * @param http HttpSecurity 객체로 보안 설정을 정의합니다.
     * @return 구성된 SecurityFilterChain 객체
     * @throws Exception 보안 설정 중 예외가 발생할 경우
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> Customizer.withDefaults()) // CORS 설정
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화
            .formLogin((form) -> form.disable()) // FormLogin 비활성화

//            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy( // 세션 정책 설정 (STATELESS)
//                SessionCreationPolicy.STATELESS)
//            )
//            .exceptionHandling(exceptionHandling -> // 인증 및 접근 거부 처리
//                    exceptionHandling
//                            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                            .accessDeniedHandler(jwtAccessDeniedHandler)
//            )
//            .headers(headers -> // H2-console 허용
//                    headers.frameOptions(frameOptions -> frameOptions.sameOrigin())
//            )
//            .addFilterBefore(new JwtFilter(customUserDetailsService, tokenRepository, tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authorize -> authorize
                            .anyRequest().permitAll()
//                            .requestMatchers(AUTH_WHITELIST).permitAll()
//                            .requestMatchers("api/login/**").permitAll()
//                            .requestMatchers("api/admin/**").hasRole("ADMIN") // 관리자 페이지 경로는 ADMIN 역할만 허용
//                            .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
            );

        return http.build();
    }

    /**
     * 비밀번호 암호화를 위한 PasswordEncoder를 Bean으로 등록합니다.
     *
     * @return BCryptPasswordEncoder 인스턴스
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CORS 설정을 정의합니다.
     *
     * - 모든 Origin, 메서드, 헤더를 허용합니다.
     * - 자격 증명 허용 설정을 활성화합니다.
     *
     * @return CORS 설정을 포함하는 CorsConfigurationSource 객체
     */
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.setAllowedOrigins(Arrays.asList("http://kernel-dev-lens.vercel.app",
//                "https://kernel-dev-lens.vercel.app",
//                "http://localhost:3000",
//                "https://www.devlens.work",
//                "https://devlens.work",
//                "https://api.devlens.work"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
//        configuration.setAllowCredentials(true);   // 자격 증명 허용
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 적용
//        return source;
//    }

}

