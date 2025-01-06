package com.seveneleven.devlens.domain.project.controller;

import com.seveneleven.devlens.domain.project.dto.GetProjectDetail;
import com.seveneleven.devlens.domain.project.dto.GetProjectStep;
import com.seveneleven.devlens.global.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "프로젝트 API", description = "프로젝트 관련 API")
public interface ProjectDocs {

    @GetMapping("/{projectId}")
    @Operation(
            summary = "프로젝트 상세 조회",
            description = "특정 프로젝트의 상세 정보를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 프로젝트 상세 정보를 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProjectDetail.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "projectId",
                            description = "조회할 프로젝트의 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    APIResponse<GetProjectDetail.Response> getProjectDetail(
            @PathVariable Long projectId
    );

    @GetMapping("/step/{projectId}")
    @Operation(
            summary = "프로젝트 단계 조회",
            description = "특정 프로젝트의 단계를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 프로젝트 단계를 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProjectStep.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "projectId",
                            description = "단계를 조회할 프로젝트의 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    APIResponse<GetProjectStep.Response> getProjectStep(
            @PathVariable Long projectId
    );
}
