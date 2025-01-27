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

    @PostMapping("/steps")
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
            @RequestBody PostProjectStep.Request requestDto
    );

    @PutMapping("/steps")
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
            @RequestBody PutProjectStep.Request requestDto
    );

    @DeleteMapping("/steps")
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
            @RequestBody DeleteProjectStep.Request requestDto
    );


    @PostMapping("/{stepId}/authorizations")
    @Operation(
            summary = "프로젝트 권한 생성",
            description = "특정 단계의 프로젝트 권한을 생성합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "프로젝트 권한이 성공적으로 생성되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostProjectAuthorization.Response.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "207",
                            description = "일부 요청이 실패하였습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostProjectAuthorization.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<PostProjectAuthorization.Response>> postProjectAuthorization(
            @PathVariable Long stepId,
            @RequestBody PostProjectAuthorization.Request requestDto
    );

    @GetMapping("/{stepId}/authorizations")
    @Operation(
            summary = "프로젝트 권한 조회",
            description = "특정 단계의 프로젝트 권한을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "프로젝트 권한이 성공적으로 조회되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProjectAuthorization.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<GetProjectAuthorization.Response>> getProjectAuthorization(
            @PathVariable Long stepId
    );
}
