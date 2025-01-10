package com.seveneleven.devlens.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 1000번대 코드 : 회원 관련
    UNAUTHORIZED(1000, HttpStatus.UNAUTHORIZED, "사용자 인증이 필요합니다."),
    FORBIDDEN(1001, HttpStatus.FORBIDDEN, "사용 권한이 없습니다."),
    NOT_FOUND_TOKEN(1002, HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않은 JWT 토큰입니다."),
    EXPIRED_TOKEN(1003, HttpStatus.INTERNAL_SERVER_ERROR, "만료된 JWT 토큰입니다."),
    JWT_FILTER_ERROR(1004, HttpStatus.INTERNAL_SERVER_ERROR, "필터 처리 중 예외가 발생했습니다."),
    USER_NOT_FOUND(1005, HttpStatus.BAD_REQUEST,"회원 정보를 찾을 수 없습니다."),
    MEMBER_INACTIVE(1006, HttpStatus.BAD_REQUEST,"비활성화된 회원입니다."),
    MEMBER_SUSPENDED(1007, HttpStatus.BAD_REQUEST,"정지된 회원입니다."),
    DUPLICATE_USER_ID(1008, HttpStatus.BAD_REQUEST,"이미 존재하는 ID 입니다."),
    DUPLICATE_EMAIL(1009, HttpStatus.BAD_REQUEST,"이미 존재하는 이메일 입니다."),
    INVALID_PASSWORD_LENGTH(1010, HttpStatus.BAD_REQUEST,"비밀번호는 8자 이상, 20자 이하로 입력해야 합니다."),
    INVALID_PASSWORD_FORMAT(1011, HttpStatus.BAD_REQUEST,"비밀번호는 숫자와 특수문자를 포함해야 합니다."),

    COMPANY_DUPLICATED_NUMBER(1051, HttpStatus.BAD_REQUEST,"이미 등록된 회사입니다."),
    COMPANY_IS_DEACTIVATED(1052, HttpStatus.BAD_REQUEST,"비활성화된 회사 정보입니다."),
    COMPANY_IS_NOT_FOUND(1053,HttpStatus.BAD_REQUEST,"회사 정보를 찾을 수 없습니다."),

    // 2000번대 코드 : 프로젝트 관련
    PROJECT_NOT_FOUND(2000,HttpStatus.BAD_REQUEST,"프로젝트 정보를 찾을 수 없습니다."),
    PROJECT_DUPLICATED_NAME(2001,HttpStatus.BAD_REQUEST, "이미 등록된 프로젝트명입니다."),
    PROJECT_HISTORY_NOT_FOUND_EXCEPTION(2002,HttpStatus.BAD_REQUEST,"프로젝트 이력을 찾을 수 없습니다."),
    // 3000번대 코드 : DB 관련


    // 4000번대 코드 : 파일 s3 버킷 업/다운로드 관련
    FILE_UPLOAD_FAIL_ERROR(4000, HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    FILE_NOT_EXIST_ERROR(4001, HttpStatus.BAD_REQUEST, "빈 파일이거나 존재하지 않는 파일입니다."),
    INVALID_FILE_NAME_ERROR(4002, HttpStatus.BAD_REQUEST, "파일명이 비었거나 유효하지 않습니다."),
    INVALID_FILE_CATEGORY_ERROR(4003, HttpStatus.BAD_REQUEST, "파일 카테고리가 유효하지 않습니다."),
    FORMAT_NOT_PERMITTED_ERROR(4004, HttpStatus.BAD_REQUEST, "유효하지 않은 파일 타입입니다."),
    MIME_NOT_PERMITTED_ERROR(4005, HttpStatus.BAD_REQUEST, "유효하지 않은 MIME 타입입니다."),
    FILE_SIZE_EXCEED_ERROR(4006, HttpStatus.BAD_REQUEST, "파일 사이즈가 초과되었습니다."),
    S3_UPLOAD_FAIL_ERROR(4007, HttpStatus.INTERNAL_SERVER_ERROR, "S3 업로드에 실패했습니다."),
    LOGO_ALREADY_EXIST_ERROR(4008, HttpStatus.BAD_REQUEST, "회사 로고가 존재합니다."),

    // 5000번대 코드 : 서버 내부 오류 관련
    INTERNAL_SERVER_ERROR(5001, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),


    // 10000 : 알 수 없는 예외
    UNKNOWN_ERROR(10000, HttpStatus.BAD_REQUEST, "알 수 없는 예외입니다,"),
    ENTITY_NOT_FOUND(10001, HttpStatus.BAD_REQUEST,"정보를 찾을 수 없습니다");
    private final Integer code;
    private final HttpStatus status;
    private final String message;

    public int getStatusCode() {
        return status.value();
    }
}
