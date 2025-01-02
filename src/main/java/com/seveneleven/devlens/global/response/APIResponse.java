package com.seveneleven.devlens.global.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class APIResponse<T> {
    private int code;
    private String message;
    private T data;

    // 성공
    public static <T> APIResponse<T> success(){
        return new APIResponse<>(SuccessCode.OK.getStatusCode(), SuccessCode.OK.getMessage(), null);
    }

    public static <T> APIResponse<T> success(T data) {
        return new APIResponse<>(SuccessCode.OK.getStatusCode(), SuccessCode.OK.getMessage(), data);
    }

    public static <T> APIResponse<T> success(SuccessCode code, T data) {
        return new APIResponse<>(code.getStatusCode(), code.getMessage(), data);
    }

    public static <T> APIResponse<T> success(SuccessCode code, String message, T data) {
        return new APIResponse<>(code.getStatusCode(), message, data);
    }

    //실패
    public static <T> APIResponse<T> fail(ErrorCode code){
        return new APIResponse<>(code.getStatusCode(), code.getMessage(), null);
    }

    public static <T> APIResponse<T> fail(ErrorCode code, String message) {
        return new APIResponse<>(code.getStatusCode(), message, null);
    }

    //생성
//    public static <T> APIResponse<T> create(T data) {
//        return new APIResponse<>(data);
//    }

    public static <T> APIResponse<T> create(int code, String message, T data) {
        return new APIResponse<>(code, message, data);
    }
}