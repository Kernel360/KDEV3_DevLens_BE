package com.seveneleven.project.controller;

import com.seveneleven.project.dto.*;
import com.seveneleven.response.APIResponse;
import com.seveneleven.util.security.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Project Checklist API", description = "프로젝트 체크리스트 관련 API")
@RequestMapping("/api/projects")
public interface ProjectChecklistDocs {

    @GetMapping("/{projectId}/steps/{stepId}")
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
            @PathVariable Long projectId,
            @PathVariable Long stepId
    );

    @PostMapping("/steps/{stepId}/checklists")
    @Operation(
            summary = "체크리스트 생성",
            description = "새로운 체크리스트를 생성합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "체크리스트가 성공적으로 생성되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostProjectChecklist.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<PostProjectChecklist.Response>> postProjectChecklist(
            @PathVariable Long stepId,
            @RequestBody PostProjectChecklist.Request request
    );

    @PutMapping("/checklists/{checklistId}")
    @Operation(
            summary = "체크리스트 수정",
            description = "기존 체크리스트를 수정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "체크리스트가 성공적으로 수정되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PutProjectChecklist.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<PutProjectChecklist.Response>> putProjectChecklist(
            @PathVariable Long checklistId,
            @RequestBody PutProjectChecklist.Request request
    );

    @DeleteMapping("/{checklistId}")
    @Operation(
            summary = "체크리스트 삭제",
            description = "특정 체크리스트를 삭제합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "체크리스트가 성공적으로 삭제되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeleteProjectChecklist.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "checklistId",
                            description = "삭제할 체크리스트의 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<DeleteProjectChecklist.Response>> deleteProjectChecklist(
            @PathVariable Long checklistId
    );

    @PostMapping("/checklists/{checklistId}/applications")
    @Operation(
            summary = "체크리스트 신청 생성",
            description = "체크리스트에 새로운 신청을 생성합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "체크리스트 신청이 성공적으로 생성되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostProjectChecklistApplication.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<PostProjectChecklistApplication.Response>> postProjectChecklistApplication(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long checklistId,
            @RequestBody PostProjectChecklistApplication.Request requestDto,
            HttpServletRequest request
    );

    @PostMapping("/applications/{applicationId}/accept")
    @Operation(
            summary = "체크리스트 신청 승인",
            description = "특정 체크리스트 신청을 승인합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "체크리스트 신청이 성공적으로 승인되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostProjectChecklistAccept.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "applicationId",
                            description = "승인할 신청의 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PostProjectChecklistAccept.Response>> postProjectChecklistAccept(
            @PathVariable Long applicationId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request
    );

    @PostMapping("/applications/{applicationId}/reject")
    @Operation(
            summary = "체크리스트 신청 거절",
            description = "특정 체크리스트 신청을 거절합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "체크리스트 신청이 성공적으로 거절되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostProjectChecklistReject.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "applicationId",
                            description = "거절할 신청의 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PostProjectChecklistReject.Response>> postProjectChecklistReject(
            @PathVariable Long applicationId,
            @RequestBody PostProjectChecklistReject.Request requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request
    );

    @GetMapping("/application/{applicationId}/result")
    @Operation(
            summary = "프로젝트 신청 결과 조회",
            description = "특정 프로젝트 신청의 결과를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 프로젝트 신청 결과를 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetApplicationResult.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "applicationId",
                            description = "결과를 조회할 프로젝트 신청의 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<GetApplicationResult.Response>> getProjectApplicationResult(
            @PathVariable Long applicationId
    );
}