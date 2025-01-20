package com.seveneleven.member.controller;

import com.seveneleven.config.JwtFilter;
import com.seveneleven.member.dto.*;
import com.seveneleven.member.service.MailService;
import com.seveneleven.member.service.MemberService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


/*
 * 로그인 페이지 API Controller
 *
 * 핵심 기능 : 로그인, 로그아웃, 이메일 인증, 비밀번호 재설정
 * */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController implements MemberDocs{

    private final MemberService memberService;
    private final MailService mailService;

    /**
     * 함수명 : login
     * 회원 로그인 처리 및 JWT 토큰 발급.
     *
     * @param request 로그인 요청 데이터를 담은 객체 (LoginRequest).
     * @return HTTP 상태 코드 200 OK와 JWT 토큰을 포함한 응답 헤더(APIResponse<SuccessCode>).
     *         Authorization 헤더에 Bearer 토큰 형식으로 JWT 토큰을 추가하여 반환합니다.
     */
    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponse>> login(@RequestBody LoginPost.Request request) {

        // 로그인 처리 및 JWT 토큰 발급
        LoginPost.Response response = memberService.login(request);

        // JWT 토큰을 Authorization 헤더에 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + response.getToken());

        // 성공 응답 반환
        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .headers(httpHeaders)
                .body(APIResponse.success(SuccessCode.OK, response.getCompanyInfo()));
    }

    /**
     * 함수명 : logout
     * 사용자 로그아웃 처리 메서드.
     * 토큰의 상태를 BLACKLISTED로 변경하여 더 이상 사용할 수 없도록 처리
     *
     * @param token 클라이언트에서 전달된 JWT 토큰 (Authorization 헤더에 포함)
     * @return 로그아웃 성공 응답
     */
    @PostMapping("/logout")
    public ResponseEntity<APIResponse<SuccessCode>> logout(@RequestHeader("Authorization") String token) {

         memberService.logout(token);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK));
    }

    /**
     * 함수명 : sendMail
     * 이메일 전송 메서드. 주어진 이메일 주소로 인증 메일을 발송하며, 생성된 인증 키를 반환합니다.
     *
     * @return 인증 키를 포함한 성공 응답
     */
    @PostMapping("/send-mail")
    public ResponseEntity<APIResponse<String>> sendMail(@AuthenticationPrincipal CustomUserDetails userDetails) {

        // 이메일 전송 및 인증 키 생성
        String key = mailService.sendEmail(userDetails);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, key));
    }

    /**
     * 함수명 : checkMail
     * 이메일 인증 확인 메서드. 사용자가 입력한 인증 키를 검증하여 성공 여부를 반환합니다.
     *
     * @param request 인증 키 확인 요청 정보를 담은 객체
     * @return 인증 성공 여부를 포함한 성공 응답
     */
    @PostMapping("/check-mail")
    public ResponseEntity<APIResponse<Boolean>> checkMail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @RequestBody CheckMailPostRequest request) {

        Boolean checkSuccess = mailService.checkMail(userDetails, request);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, checkSuccess));
    }

    /**
     * 함수명 : resetPwd
     * 회원 비밀번호를 재설정합니다.
     *
     * @param request 재설정할 회원 정보 Dto(RequestBody).
     * @return 초기화된 임시 비밀번호를 포함한 응답 객체 (APIResponse<MemberPatch.Response>).
     *         HTTP 상태 코드는 200 OK로 반환됩니다.
     */
    @PatchMapping("/members/reset-password")
    public ResponseEntity<APIResponse<MemberPatch.Response>> resetPwd(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                      @RequestBody MemberPatch.Request request) {

        // 비밀번호 초기화
        MemberPatch.Response response = memberService.resetPassword(userDetails, request);

        // 응답으로 임시 비밀번호 반환
        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, response));
    }
}