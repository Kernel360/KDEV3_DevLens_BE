package com.seveneleven.board.controller;

import com.seveneleven.board.dto.*;
import com.seveneleven.board.service.CommentService;
import com.seveneleven.board.service.PostFileService;
import com.seveneleven.board.service.PostServiceImpl;
import com.seveneleven.entity.board.constant.PostFilter;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.PageResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.file.dto.FileMetadataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class BoardController implements BoardDocs {
    private final PostServiceImpl postService;
    private final CommentService commentService;
    private final PostFileService postFileService;

    /**
     * 함수명 : selectList()
     * 게시글 목록을 조회하는 메서드
     */
    @GetMapping("/steps/{projectStepId}")
    public ResponseEntity<APIResponse<PageResponse<PostListResponse>>> selectList (@PathVariable Long projectStepId,
                                                                                   @RequestParam(defaultValue = "0") Integer page,
                                                                                   @RequestParam(required = false) String keyword,
                                                                                   @RequestParam(required = false) PostFilter filter
                                                                                   // todo: 정렬기준 추후 추가 예정
    ) throws Exception {
        PageResponse<PostListResponse> postList = postService.selectPostList(projectStepId, page, keyword, filter);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, postList));
    }

    /**
     * 함수명 : selectPost()
     * 게시글 상세를 조회하는 메서드
     */
    @GetMapping("/{postId}")
    @Override
    public ResponseEntity<APIResponse<PostResponse>> selectPost(@PathVariable Long postId) throws Exception {
        PostResponse postResponse = postService.selectPost(postId);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                        .body(APIResponse.success(SuccessCode.OK, postResponse));
    }

    /**
     * 함수명 : selectPostFiles
     * 게시글의 파일 목록을 불러오는 메서드
     */
    @GetMapping("/{postId}/files")
    public ResponseEntity<APIResponse<List<FileMetadataDto>>> selectPostFiles(@PathVariable Long postId) throws Exception {
        List<FileMetadataDto> fileDataDtos = postFileService.getPostFiles(postId);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, fileDataDtos));
    }

    /**
     * 함수명 : createPost()
     * 게시글을 생성하는 메서드
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> createPost(@RequestPart PostCreateRequest postCreateRequest,
                                                               @RequestPart(required = false) List<MultipartFile> files
    ) throws Exception {
        postService.createPost(postCreateRequest, files);

        return ResponseEntity.status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED));
    }

    /**
     * 함수명 : updatePost()
     * 게시글을 수정하는 메서드
     */
    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> updatePost(@PathVariable Long postId,
                                                               @RequestPart PostUpdateRequest postUpdateRequest,
                                                               @RequestPart(required = false) List<MultipartFile> files
    ) throws Exception {
        postService.updatePost(postUpdateRequest, files);

        return ResponseEntity.status(SuccessCode.UPDATED.getStatus())
                .body(APIResponse.success(SuccessCode.UPDATED));
    }

    /**
     * 함수명 : deletePost()
     * 게시글을 삭제하는 메서드
     */
    @DeleteMapping("/{postId}/{registerId}")
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> deletePost(@PathVariable Long postId,
                                                               @PathVariable Long registerId
    ) throws Exception {
        postService.deletePost(postId, registerId);
        return ResponseEntity.status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }


}
