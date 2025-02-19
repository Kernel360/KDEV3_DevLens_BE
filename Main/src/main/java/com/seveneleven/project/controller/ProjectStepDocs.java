package com.seveneleven.project.controller;

import com.seveneleven.project.dto.*;
import com.seveneleven.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Project Step API", description = "프로젝트 단계 관련 API")
@RequestMapping("/api/projects")
public interface ProjectStepDocs {

    @GetMapping("/{projectId}/steps")
    @Operation(
            summary = "프로젝트 내 모든 단계 및 체크리스트 조회",
            description = "해당 프로젝트의 모든 단계와 체크리스트를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 단계와 체크리스트를 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProjectStep.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<GetProjectStep.Response>> getProjectStepAndChecklist(
            @PathVariable Long projectId
    );

    @PostMapping("/{projectId}/steps")
    @Operation(
            summary = "프로젝트 단계 추가",
            description = "특정 프로젝트의 단계를 추가합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "프로젝트 단계가 성공적으로 추가되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostProjectStep.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<PostProjectStep.Response>> postProjectStep(
            @PathVariable Long projectId,
            @RequestBody PostProjectStep.Request requestDto
    );

    @PutMapping("/{projectId}/steps/{stepId}")
    @Operation(
            summary = "프로젝트 단계 수정",
            description = "특정 프로젝트의 단계를 수정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "프로젝트 단계가 성공적으로 수정되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PutProjectStep.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<PutProjectStep.Response>> putProjectStep(
            @PathVariable Long projectId,
            @PathVariable Long stepId,
            @RequestBody PutProjectStep.Request requestDto
    );

    @DeleteMapping("/{projectId}/steps/{stepId}")
    @Operation(
            summary = "프로젝트 단계 삭제",
            description = "특정 프로젝트 단계를 삭제합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "프로젝트 단계가 성공적으로 삭제되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeleteProjectStep.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<DeleteProjectStep.Response>> deleteProjectStep(
            @PathVariable Long projectId,
            @PathVariable Long stepId
    );
}
