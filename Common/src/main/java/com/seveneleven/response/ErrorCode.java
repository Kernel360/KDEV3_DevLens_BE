package com.seveneleven.response;

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
    USER_NOT_FOUND(1005, HttpStatus.NOT_FOUND,"회원 정보를 찾을 수 없습니다."),
    MEMBER_INACTIVE(1006, HttpStatus.BAD_REQUEST,"비활성화된 회원입니다."),
    MEMBER_SUSPENDED(1007, HttpStatus.BAD_REQUEST,"정지된 회원입니다."),
    MEMBER_WITHDRAW(1008, HttpStatus.BAD_REQUEST,"탈퇴한 회원입니다."),
    DUPLICATE_USER_ID(1009, HttpStatus.BAD_REQUEST,"이미 존재하는 ID 입니다."),
    DUPLICATE_EMAIL(1010, HttpStatus.BAD_REQUEST,"이미 존재하는 이메일 입니다."),
    INVALID_PASSWORD_LENGTH(1011, HttpStatus.BAD_REQUEST,"비밀번호는 8자 이상, 20자 이하로 입력해야 합니다."),
    INVALID_PASSWORD_FORMAT(1012, HttpStatus.BAD_REQUEST,"비밀번호는 숫자와 특수문자를 포함해야 합니다."),
    INCORRECT_PASSWORD(1013, HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
    UNABLE_TO_SEND_EMAIL(1014, HttpStatus.BAD_REQUEST, "이메일 전송을 실패했습니다."),
    EMAIL_AUTH_FAILED(1015, HttpStatus.BAD_REQUEST, "이메일 인증을 실패했습니다."),
    PROFILE_IMAGE_ALREADY_EXIST(1016, HttpStatus.BAD_REQUEST, "프로필 이미지가 존재합니다."),

    COMPANY_DUPLICATED_NUMBER(1051, HttpStatus.BAD_REQUEST,"이미 등록된 회사입니다."),
    COMPANY_IS_DEACTIVATED(1052, HttpStatus.BAD_REQUEST,"비활성화된 회사 정보입니다."),
    COMPANY_IS_NOT_FOUND(1053,HttpStatus.BAD_REQUEST,"회사 정보를 찾을 수 없습니다."),

    NOT_FOUND_DEPARTMENT(1060, HttpStatus.NOT_FOUND, "해당 부서를 찾을 수 없습니다."),
    NOT_FOUND_POSITION(1061, HttpStatus.NOT_FOUND, "해당 직책을 찾을 수 없습니다."),

    // 2000번대 코드 : 프로젝트 관련
    PROJECT_NOT_FOUND(2000,HttpStatus.BAD_REQUEST,"프로젝트 정보를 찾을 수 없습니다."),
    PROJECT_DUPLICATED_NAME(2001,HttpStatus.BAD_REQUEST, "이미 등록된 프로젝트명입니다."),
    PROJECT_HISTORY_NOT_FOUND_EXCEPTION(2002,HttpStatus.BAD_REQUEST,"프로젝트 이력을 찾을 수 없습니다."),
    CHECKLIST_NOT_FOUND(2100, HttpStatus.BAD_REQUEST, "체크리스트가 없습니다."),
    CHECKLIST_ALREADY_CHECKED(2101, HttpStatus.BAD_REQUEST, "이미 체크된 체크리스트입니다."),
    CHECKLIST_ALREADY_DELETED(2101, HttpStatus.BAD_REQUEST, "이미 삭제된 체크리스트입니다."),
    CHECK_REQUEST_NOT_FOUND(2200, HttpStatus.BAD_REQUEST, "승인 요청을 찾을 수 없습니다."),
    CHECK_REQUEST_ALREADY_HAS_RESULT(2201, HttpStatus.BAD_REQUEST, "이미 처리된 승인 요청입니다."),
    PROJECT_STEP_ALREADY_DELETED(2301, HttpStatus.BAD_REQUEST, "이미 삭제된 단계입니다."),

    NOT_FOUND_PROJECT_STEP(2501, HttpStatus.NOT_FOUND, "해당 프로젝트 단계를 찾을 수 없습니다."),
    NOT_FOUND_MEMBER(2502, HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    NOT_FOUND_POST(2503, HttpStatus.NOT_FOUND, "해당 게시물을 찾을 수 없습니다."),
    NOT_AUTHORIZED_TO_POST(2504, HttpStatus.FORBIDDEN, "작성자만 게시글을 수정 및 삭제할 수 있습니다."),
    NOT_FOUND_WRITER(2505, HttpStatus.NOT_FOUND, "작성자 정보를 찾을 수 없습니다."),
    NOT_DELETE_PARENT_POST(2506, HttpStatus.BAD_REQUEST, "관련된 게시글이 존재하는 경우 해당 게시글을 삭제할 수 없습니다."),
    NOT_MATCH_PROJECTSTEPID(2507, HttpStatus.BAD_REQUEST, "게시글의 프로젝트 단계가 일치하지 않습니다."),
    // 3000번대 코드 : DB 관련
    FILE_NOT_FOUND_ERROR(3000, HttpStatus.NOT_FOUND, "해당 파일을 찾을 수 없습니다."),

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
