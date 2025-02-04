package com.seveneleven.board.controller;

import com.seveneleven.board.dto.*;
import com.seveneleven.entity.board.constant.PostFilter;
import com.seveneleven.entity.board.constant.PostSort;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.PaginatedResponse;
import com.seveneleven.response.SuccessCode;
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

@Tag(name = "Post API", description = "게시글 관련 API")
public interface BoardDocs {
    // 목록 조회
    @Operation(
            summary = "게시글 목록 조회",
            description = "게시글 목록을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 목록 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostListResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "projectStepId",
                            description = "프로젝트 단계 ID",
                            required = true,
                            example = "1"
                    ),
                    @Parameter(
                            name = "page",
                            description = "현재 위치한 페이지 (0부터 시작)",
                            required = true,
                            example = "0"
                    ),
                    @Parameter(
                            name = "keyword",
                            description = "검색 키워드",
                            required = false,
                            example = "기능정의서"
                    ),
                    @Parameter(
                            name = "filter",
                            description = "필터 조건 (ALL, TITLE, CONTENT, WRITER)",
                            required = false,
                            example = "ALL"
                    ),
                    @Parameter(
                            name = "sortType",
                            description = "정렬 조건 (NEWEST, OLDEST)",
                            required = false,
                            example = "NEWEST"
                    )
            }
    )
    @GetMapping("/steps/{projectStepId}")
    ResponseEntity<APIResponse<PaginatedResponse<PostListResponse>>> selectList (@PathVariable Long projectStepId,
                                                                                 @RequestParam(defaultValue = "0") Integer page,
                                                                                 @RequestParam(required = false) String keyword,
                                                                                 @RequestParam(defaultValue = "ALL", required = false) PostFilter filter,
                                                                                 @RequestParam(defaultValue = "NEWEST", required = false) PostSort sortType
    );

    // 조회
    @Operation(
            summary = "게시글 상세 조회",
            description = "게시글을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "조회할 게시글의 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    @GetMapping("/{postId}")
    ResponseEntity<APIResponse<PostResponse>> selectPost(@PathVariable Long postId);

    // 생성
    @Operation(
            summary = "게시글 생성",
            description = "새로운 게시글을 생성합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "게시글 생성 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            }
    )
    @PostMapping()
    ResponseEntity<APIResponse<SuccessCode>> createPost(@AuthenticationPrincipal CustomUserDetails user,
                                                        @RequestBody PostCreateRequest postCreateRequest,
                                                        HttpServletRequest request

    );

    // 수정
    @Operation(
            summary = "게시물 수정",
            description = "기존 게시글을 수정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 수정 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "수정할 게시글의 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    @PutMapping("/{postId}")
    ResponseEntity<APIResponse<SuccessCode>> updatePost(@AuthenticationPrincipal CustomUserDetails user,
                                                        @PathVariable Long postId,
                                                        @RequestBody PostUpdateRequest postUpdateRequest,
                                                        HttpServletRequest request
    );

    // 삭제
    @Operation(
            summary = "게시글 삭제",
            description = "기존 게시글을 삭제합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 삭제 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{postId}/{registerId}")
    ResponseEntity<APIResponse<SuccessCode>> deletePost(@AuthenticationPrincipal CustomUserDetails user,
                                                        @PathVariable Long postId,
                                                        HttpServletRequest request
    );
}
