package com.seveneleven.config.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.util.GetIpUtil;
import com.seveneleven.util.security.dto.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Slf4j
@Component
public class ControllerAop {

    private final GetIpUtil getIpUtil = new GetIpUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("execution(* com.seveneleven.*.controller..*Controller.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = null;

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        }
        Object[] args = joinPoint.getArgs();
        String params;
        try {
            params = objectMapper.writeValueAsString(args);
        } catch (Exception e) {
            // 변환 실패 시 Arrays.toString() 사용
            params = Arrays.toString(args);
        }

        log.info("Controller Called - Request : {} {} | Parameters : {} | RequesterId : {} | Requester Ip : {}", request.getMethod(), request.getRequestURI(), params, userDetails != null ? userDetails.getId() : "", getIpUtil.getIpAddress(request));

        try {
            Object result = joinPoint.proceed();
            if(result instanceof ResponseEntity<?> responseEntity) {
                Object responseBody = responseEntity.getBody();
                if (responseBody != null) {
                    log.info("Controller End - Response : {} {} | Response Code : {}, Response Body : {} | RequesterId : {} | Requester Ip : {}", request.getMethod(), request.getRequestURI(),((ResponseEntity<?>) result).getStatusCode() , responseBody.toString(), userDetails != null ? userDetails.getId() : "", getIpUtil.getIpAddress(request));
                }
            }
            return result;
        } catch (BusinessException bs) {
            log.error("request uri : {}, error : {}", joinPoint.getSignature().getName(), bs.getMessage());
            throw bs;
        }
    }
}
