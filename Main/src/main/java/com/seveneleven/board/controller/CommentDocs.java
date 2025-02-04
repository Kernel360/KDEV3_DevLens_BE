package com.seveneleven.board.controller;

import com.seveneleven.board.dto.PatchCommentRequest;
import com.seveneleven.board.dto.PostCommentRequest;
import com.seveneleven.response.APIResponse;
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

@Tag(name = "Comment API", description = "댓글 관련 API")
public interface CommentDocs {
    @Operation(
            summary = "댓글 생성",
            description = "댓글을 생성합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "댓글 생성 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "게시글 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    @PostMapping()
    ResponseEntity<APIResponse<SuccessCode>> createComment(@AuthenticationPrincipal CustomUserDetails user,
                                                           @PathVariable Long postId,
                                                           @RequestBody PostCommentRequest postCommentRequest,
                                                           HttpServletRequest request
    );

    @Operation(
            summary = "댓글 수정",
            description = "댓글을 수정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "댓글 수정 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "게시글 ID",
                            required = true,
                            example = "1"
                    ),
                    @Parameter(
                            name = "commentId",
                            description = "댓글 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    @PatchMapping("/{commentId}")
    ResponseEntity<APIResponse<SuccessCode>> updateComment(@AuthenticationPrincipal CustomUserDetails user,
                                                           @PathVariable Long postId,
                                                           @PathVariable Long commentId,
                                                           @RequestBody PatchCommentRequest patchCommentRequest,
                                                           HttpServletRequest request
    );

    @Operation(
            summary = "댓글 삭제",
            description = "댓글을 삭제합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "댓글 삭제 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "게시글 ID",
                            required = true,
                            example = "1"
                    ),
                    @Parameter(
                            name = "commentId",
                            description = "댓글 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    @DeleteMapping("{commentId}")
    ResponseEntity<APIResponse<SuccessCode>> deleteComment(@AuthenticationPrincipal CustomUserDetails user,
                                                           @PathVariable Long postId,
                                                           @PathVariable Long commentId,
                                                           HttpServletRequest request
    );
}
