package com.seveneleven.exception;

import com.seveneleven.response.APIResponse;
import com.seveneleven.response.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<APIResponse<?>> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(APIResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse<?>> handleAccexxDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(ErrorCode.FORBIDDEN.getStatusCode())
                .body(APIResponse.fail(ErrorCode.FORBIDDEN));
    }

    // 기타 예외에 대한 처리 예시
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<?>> handleException(Exception e, WebRequest request) {
        // ErrorCode를 사용하여 에러 응답 생성
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR; // 공통 에러 코드 정의 필요
        String detailedMessage = e.getMessage() != null ? e.getMessage() : "An unexpected error occurred";
        APIResponse<?> response = APIResponse.fail(errorCode, detailedMessage);

        log.error(detailedMessage, e);
        return ResponseEntity
                .status(errorCode.getStatusCode()) // HTTP 상태 코드 설정
                .body(response); // APIResponse를 응답 본문으로 설정
    }
}