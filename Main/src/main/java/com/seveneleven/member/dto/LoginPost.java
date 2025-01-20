package com.seveneleven.member.dto;

import lombok.*;

/*
 * 로그인 API DTO
 *
 * */
public class LoginPost {

    @Getter
    public static class Request{
        private String loginId;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private String token;
        private LoginResponse companyInfo;
    }
}
