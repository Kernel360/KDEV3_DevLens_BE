package com.seveneleven.project.controller;

import com.seveneleven.project.dto.*;
import com.seveneleven.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "프로젝트 체크리스트 API", description = "프로젝트 체크리스트 관련 API")
@RequestMapping("/api/projects/checklists")
public interface ProjectChecklistDocs {

    @GetMapping("/{projectId}")
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

    @GetMapping("/{stepId}")
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

    @PostMapping("")
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
            @RequestBody PostProjectChecklist.Request request
    );

    @PutMapping("")
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

    @PostMapping(value = "/applications", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
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
            @RequestBody PostProjectChecklistApplication.Request requestDto,
            @RequestBody List<MultipartFile> files,
            HttpServletRequest request
    );

    @PostMapping("/accept/{applicationId}")
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
            HttpServletRequest request
    );

    @PostMapping("/reject/{applicationId}")
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
            @PathVariable Long applicationId
    );
}