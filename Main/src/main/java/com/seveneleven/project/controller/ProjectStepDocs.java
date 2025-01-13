package com.seveneleven.project.controller;

import com.seveneleven.project.dto.*;
import com.seveneleven.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "프로젝트 단계 API", description = "프로젝트 단계 관련 API")
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
            },
            parameters = {
                    @Parameter(
                            name = "projectId",
                            description = "프로젝트 단계를 조회할 단계의 ID",
                            required = true,
                            example = "1"
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
            },
            parameters = {
                    @Parameter(
                            name = "requestDto",
                            description = "프로젝트 단계 추가 요청 데이터",
                            required = true,
                            schema = @Schema(implementation = PostProjectStep.Request.class)
                    )
            }
    )
    ResponseEntity<APIResponse<PostProjectStep.Response>> postProjectStep(
            @RequestBody PostProjectStep.Request requestDto
    );

    @GetMapping("/steps/{stepId}")
    @Operation(
            summary = "체크리스트 조회",
            description = "특정 단계의 체크리스트를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 체크리스트를 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetStepChecklist.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "stepId",
                            description = "체크리스트를 조회할 단계의 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<GetStepChecklist.Response>> getProjectChecklist(
            @PathVariable Long stepId
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
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 프로젝트 단계의 요청 데이터",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PutProjectStep.Request.class)
                    )
            )
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
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "삭제할 프로젝트 단계의 요청 데이터",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeleteProjectStep.Request.class)
                    )
            )
    )
    ResponseEntity<APIResponse<DeleteProjectStep.Response>> deleteProjectStep(
            @RequestBody DeleteProjectStep.Request requestDto
    );

}
