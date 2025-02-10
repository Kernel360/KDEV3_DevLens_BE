package com.seveneleven.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.jackson.TypeNameResolver;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    private final ObjectMapper objectMapper;

    public SwaggerConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void initialize() {
        TypeNameResolver innerClassAwareTypeNameResolver = new TypeNameResolver() {
            @Override
            public String getNameOfClass(Class<?> cls) {
                /*
                    cls.getName() -> com.seveneleven.project.dto.GetProjectList$Request
                    subString(lastIndexOf(".") + 1) -> GetProjectList$Request
                    replace("$", ".") -> GetProjectList.Request
                 */
                return cls.getName()
                        .substring(cls.getName().lastIndexOf(".") + 1)
                        .replace("$", "");
            }
        };

        ModelConverters.getInstance()
                .addConverter(new ModelResolver(objectMapper, innerClassAwareTypeNameResolver));
    }
//    @Bean
//    public OpenAPI accessApi() {
//        // Access Token (Authorization 헤더)
//        SecurityScheme accessTokenScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.APIKEY)
//                .in(SecurityScheme.In.HEADER)
//                .name("Authorization");
//
//        // Refresh Token (X-Refresh-Token 헤더)
//        SecurityScheme refreshTokenScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.APIKEY)
//                .in(SecurityScheme.In.HEADER)
//                .name("X-Refresh-Token");
//
//        // 쿠키 기반 인증 (Session Cookie)
//        SecurityScheme cookieAuthScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.APIKEY)
//                .in(SecurityScheme.In.COOKIE)
//                .name("SESSIONID");
//
//        // Security 요구사항 설정 (Bearer Token, Refresh Token, Cookie Auth)
//        SecurityRequirement securityRequirement = new SecurityRequirement()
//                .addList("Bearer Token")
//                .addList("Refresh Token")
//                .addList("CookieAuth");
//
//        return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes("Bearer Token", accessTokenScheme)
//                        .addSecuritySchemes("Refresh Token", refreshTokenScheme)
//                        .addSecuritySchemes("CookieAuth", cookieAuthScheme)) // 쿠키 인증 추가
//                .addSecurityItem(securityRequirement);
//    }
}
