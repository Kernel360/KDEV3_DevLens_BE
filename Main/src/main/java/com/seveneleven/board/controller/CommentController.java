package com.seveneleven.board.controller;

import com.seveneleven.board.dto.PatchCommentRequest;
import com.seveneleven.board.dto.PostCommentRequest;
import com.seveneleven.board.service.CommentService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.dto.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController implements CommentDocs {

    private final CommentService commentService;

    @PostMapping()
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> createComment(@AuthenticationPrincipal CustomUserDetails user,
                                                                  @PathVariable Long postId,
                                                                  @Valid @RequestBody PostCommentRequest postCommentRequest,
                                                                  HttpServletRequest request
    ) {
        commentService.createComment(postId, postCommentRequest, request, user.getMember().getId());
        return ResponseEntity.status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED));
    }

    @PatchMapping("/{commentId}")
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> updateComment(@AuthenticationPrincipal CustomUserDetails user,
                                                                  @PathVariable Long postId,
                                                                  @PathVariable Long commentId,
                                                                  @RequestBody PatchCommentRequest patchCommentRequest,
                                                                  HttpServletRequest request
    ) {
        commentService.updateComment(postId, commentId, patchCommentRequest, request, user.getMember().getId());
        return ResponseEntity.status(SuccessCode.UPDATED.getStatus())
                .body(APIResponse.success(SuccessCode.UPDATED));
    }

    @DeleteMapping("{commentId}")
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> deleteComment(@AuthenticationPrincipal CustomUserDetails user,
                                                                  @PathVariable Long postId,
                                                                  @PathVariable Long commentId,
                                                                  HttpServletRequest request
    ) {
        commentService.deleteComment(postId, commentId, request, user.getMember().getId());
        return ResponseEntity.status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }

}
