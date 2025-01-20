package com.seveneleven.member.controller;

import com.seveneleven.member.dto.*;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Tag(name = "Login Page API", description = "APIs Related to Login Page")
public interface MemberDocs {
    @Operation(
            summary = "회원 로그인",
            description = "회원 로그인 처리 및 JWT 토큰 발급.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인 성공 및 JWT 토큰 발급",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class)
                            )
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "로그인 요청 데이터",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginPost.Request.class)
                    )
            )
    )
    @PostMapping("/login")
    ResponseEntity<APIResponse<LoginResponse>> login(@RequestBody LoginPost.Request request);

    @Operation(
            summary = "회원 로그아웃",
            description = "사용자 로그아웃 처리. 토큰의 상태를 BLACKLISTED로 변경하여 더 이상 사용할 수 없도록 처리.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessCode.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "클라이언트에서 전달된 JWT 토큰",
                            required = true,
                            example = "Bearer <JWT_TOKEN>"
                    )
            }
    )
    @PostMapping("/logout")
    ResponseEntity<APIResponse<SuccessCode>> logout(@RequestHeader("Authorization") String token);

    @Operation(
            summary = "이메일 인증 번호 전송",
            description = "주어진 이메일 주소로 인증 메일을 발송하며, 생성된 인증 키를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    @PostMapping("/send-mail")
    ResponseEntity<APIResponse<String>> sendMail(@AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(
            summary = "이메일 인증 확인",
            description = "사용자가 입력한 인증 키를 검증하여 성공 여부를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Boolean.class)
                            )
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "인증 키 확인 요청 데이터",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CheckMailPostRequest.class)
                    )
            )
    )
    @PostMapping("/check-mail")
    ResponseEntity<APIResponse<Boolean>> checkMail(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CheckMailPostRequest request);

    @Operation(
            summary = "비밀번호 재설정",
            description = "회원 비밀번호를 재설정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "비밀번호 재설정 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberPatch.Response.class)
                            )
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "비밀번호 재설정 요청 데이터",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MemberPatch.Request.class)
                    )
            )
    )
    @PatchMapping("/members/reset-password")
    ResponseEntity<APIResponse<MemberPatch.Response>> resetPwd(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                               @RequestBody MemberPatch.Request request);

}
