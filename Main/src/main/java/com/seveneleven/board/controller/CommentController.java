package com.seveneleven.board.controller;

import com.seveneleven.board.dto.DeleteCommentRequest;
import com.seveneleven.board.dto.PatchCommentRequest;
import com.seveneleven.board.dto.PostCommentRequest;
import com.seveneleven.board.service.CommentServiceImpl;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController implements CommentDocs {

    private final CommentServiceImpl commentService;

    @PostMapping()
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> createComment(@PathVariable Long postId,
                                                                  @RequestBody PostCommentRequest postCommentRequest
    ) throws Exception {
        commentService.createComment(postId, postCommentRequest);
        return ResponseEntity.status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED));
    }

    @PatchMapping("/{commentId}")
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> updateComment(@PathVariable Long postId,
                                                                  @PathVariable Long commentId,
                                                                  @RequestBody PatchCommentRequest patchCommentRequest
    ) throws Exception {
        commentService.updateComment(postId, commentId, patchCommentRequest);
        return ResponseEntity.status(SuccessCode.UPDATED.getStatus())
                .body(APIResponse.success(SuccessCode.UPDATED));
    }

    @DeleteMapping("{commentId}")
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> deleteComment(@PathVariable Long postId,
                                                                  @PathVariable Long commentId,
                                                                  @RequestBody DeleteCommentRequest deleteCommentRequest
    ) throws Exception {
        commentService.deleteComment(postId, commentId, deleteCommentRequest);
        return ResponseEntity.status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }

}
