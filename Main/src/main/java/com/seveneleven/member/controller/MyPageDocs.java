package com.seveneleven.member.controller;



import com.seveneleven.member.dto.MyPageGetMember;
import com.seveneleven.member.dto.PatchMember;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/members")
@Tag(name = "My Page API", description = "APIs Related to My Page")
public interface MyPageDocs {
    @Operation(
            summary = "회원 상세 정보 조회",
            description = "특정 회원의 로그인 ID를 기반으로 상세 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 상세 정보 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MyPageGetMember.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "loginId",
                            description = "조회할 회원의 로그인 ID",
                            required = true,
                            example = "john123"
                    )
            }
    )
    @GetMapping("/{loginId}")
    ResponseEntity<APIResponse<MyPageGetMember>> memberDetail(@PathVariable String loginId);

    @Operation(
            summary = "회원 정보 수정",
            description = "회원의 로그인 ID를 기반으로 회원 정보를 수정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 정보 수정 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PatchMember.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "loginId",
                            description = "수정할 회원의 로그인 ID",
                            required = true,
                            example = "john123"
                    )
            }
    )
    @PatchMapping("/{loginId}")
    ResponseEntity<APIResponse<PatchMember.Response>> updateMember( @PathVariable String loginId,
                                                                    @RequestBody PatchMember.Request requestDto);


    @Operation(
            summary = "회원 탈퇴",
            description = "회원의 로그인 ID를 기반으로 회원 상태를 변경하여 탈퇴 처리합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 탈퇴 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessCode.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "loginId",
                            description = "탈퇴할 회원의 로그인 ID",
                            required = true,
                            example = "john123"
                    )
            }
    )
    @DeleteMapping("/{loginId}")
    ResponseEntity<APIResponse<SuccessCode>> deleteMember(@PathVariable String loginId);

}
