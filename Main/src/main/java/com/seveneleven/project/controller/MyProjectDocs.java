package com.seveneleven.project.controller;

import com.seveneleven.project.dto.GetProjectList;
import com.seveneleven.response.APIResponse;
import com.seveneleven.util.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/project")
@Tag(name = "Project Dashboard API", description = "프로젝트 대시보드 관련 API")
public interface MyProjectDocs {

    @GetMapping("")
    @Operation(
            summary = "프로젝트 목록 조회",
            description = "사용자의 프로젝트 목록과 현재 회사의 프로젝트 목록을 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 프로젝트 목록을 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProjectList.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<GetProjectList.Response>> getMyProject(
            @AuthenticationPrincipal CustomUserDetails userDetails
            );
}
