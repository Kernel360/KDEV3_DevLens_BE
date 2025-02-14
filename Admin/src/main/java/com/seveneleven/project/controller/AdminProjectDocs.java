package com.seveneleven.project.controller;

import com.seveneleven.project.dto.*;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Administrator Project Management API", description = "관리자 프로젝트 관리 API")
@RequestMapping("/api/admin/projects")
public interface AdminProjectDocs {

    @PostMapping("")
    @Operation(
            summary = "프로젝트 생성",
            description = "프로젝트를 생성하는 api",
            responses =
                    {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "성공적으로 프로젝트를 생성했습니다",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PostProject.Response.class)
                                    )
                            )
                    }
    )
    ResponseEntity<APIResponse<PostProject.Response>> newProject(@RequestBody @Valid PostProject.Request request);

    @GetMapping("/check")
    @Operation(
            summary = "프로젝트 중복 체크",
            description = "프로젝트 이름으로 중복체크를 함",
            responses =
                    {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "성공적으로 중복 체크를 했습니다"
                            )
                    },
            parameters = {
                    @Parameter(
                            name = "name",
                            description = "중복체크하고자 하는 이름",
                            required = true,
                            example = "신규 프로젝트"
                    )
            }
    )
    ResponseEntity<APIResponse<String>> checkProjectName(@RequestParam String name);

    @GetMapping("/{id}")
    @Operation(
            summary = "프로젝트 상세 조회",
            description = "프로젝트 id를 통해 프로젝트 상세 조회함",
            responses =
                    {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "성공적으로 프로젝트 상세를 반환했습니다",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetProject.Response.class)
                                    )
                            )
                    },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "존재하는 프로젝트의 id",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<GetProject.Response>> readProject(@PathVariable Long id);

    @GetMapping("")
    @Operation(
            summary = "프로젝트 페이지 조회",
            description = "프로젝트 페이지 리스트를 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 프로젝트 페이지를 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProject.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "프로젝트 페이지 번호",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PaginatedResponse<GetProjectList.Response>>> getListOfProjects(@RequestParam(value = "page") Integer page);

    @GetMapping("/histories")
    @Operation(
            summary = "프로젝트 이력 페이지 조회",
            description = "프로젝트 이력 페이지를 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "프로젝트 이력 페이지를 반환했습니다",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProject.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "프로젝트 이력 페이지",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PaginatedResponse<GetProjectHistory.Response>>> getListOfProjectHistory(@RequestParam(value = "page") Integer page);

    @GetMapping("/{id}/histories")
    @Operation(
            summary = "프로젝트 이력 상세 조회",
            description = "프로젝트 이력을 프로젝트 이력 id로 상세 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "프로젝트 이력을 성공적으로 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProjectHistory.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "프로젝트 이력 id",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<GetProjectHistory.Response>> getProjectHistory(@PathVariable Long id);

    @GetMapping("/histories/search")
    @Operation(
            summary = "프로젝트 이력 검색",
            description = "프로젝트 이력 프로젝트 명으로 검색",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully searched project histories",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProjectHistory.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "searchTerm",
                            description = "프로젝트 이름",
                            required = true,
                            example = "ai"
                    ),
                    @Parameter(
                            name = "page",
                            description = "페이지 번호",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PaginatedResponse<GetProjectHistory.Response>>> searchHistories(
            @RequestParam String searchTerm,
            @RequestParam Integer page
    );

    @PutMapping("/{id}")
    @Operation(
            summary = "프로젝트 업데이트",
            description = "프로젝트 업데이트",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 프로젝트를 업데이트했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PutProject.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "프로젝트 id",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PutProject.Response>> updateProject(@PathVariable Long id, @RequestBody @Valid PutProject.Request request);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "프로젝트 삭제",
            description = "프로젝트 삭제",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 프로젝트를 삭제했습니다."
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "프로젝트의 id",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<Void>> deleteProject(@PathVariable Long id);
}
