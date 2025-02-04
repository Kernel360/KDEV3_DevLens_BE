package com.seveneleven.board.controller;

import com.seveneleven.board.dto.*;
import com.seveneleven.board.service.CommentService;
import com.seveneleven.board.service.PostFileService;
import com.seveneleven.board.service.PostLinkService;
import com.seveneleven.board.service.PostService;
import com.seveneleven.entity.board.constant.PostFilter;
import com.seveneleven.entity.board.constant.PostSort;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.PaginatedResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.file.dto.FileMetadataDto;
import com.seveneleven.util.file.dto.LinkInput;
import com.seveneleven.util.file.dto.LinkResponse;
import com.seveneleven.util.security.dto.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class BoardController implements BoardDocs {

    private final PostService postService;
    private final CommentService commentService;
    private final PostFileService postFileService;
    private final PostLinkService postLinkService;

    /**
     * 함수명 : selectList()
     * 게시글 목록을 조회하는 메서드
     */
    @GetMapping("/steps/{projectStepId}")
    public ResponseEntity<APIResponse<PaginatedResponse<PostListResponse>>> selectList (@PathVariable Long projectStepId,
                                                                                        @RequestParam(defaultValue = "0") Integer page,
                                                                                        @RequestParam(required = false) String keyword,
                                                                                        @RequestParam(defaultValue = "ALL", required = false) PostFilter filter,
                                                                                        @RequestParam(defaultValue = "NEWEST", required = false) PostSort sortType
    ) {
        PaginatedResponse<PostListResponse> postList = postService.selectPostList(projectStepId, page, keyword, filter, sortType);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, postList));
    }

    /**
     * 함수명 : selectPost()
     * 게시글 상세를 조회하는 메서드
     */
    @GetMapping("/{postId}")
    @Override
    public ResponseEntity<APIResponse<PostResponse>> selectPost(@PathVariable Long postId) {
        PostResponse postResponse = postService.selectPost(postId);
        postResponse.setComments(commentService.selectCommentList(postId));

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                        .body(APIResponse.success(SuccessCode.OK, postResponse));
    }

    /**
     * 함수명 : selectPostLinks
     * 게시글의 링크 목록을 불러오는 메서드
     */
    @GetMapping("/{postId}/links")
    public ResponseEntity<APIResponse<List<LinkResponse>>> selectPostLinks(@PathVariable Long postId) {
        List<LinkResponse> postLists = postLinkService.getPostLinks(postId);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, postLists));
    }

    /**
     * 함수명 : selectPostFiles
     * 게시글의 파일 목록을 불러오는 메서드
     */
    @GetMapping("/{postId}/files")
    public ResponseEntity<APIResponse<List<FileMetadataDto>>> selectPostFiles(@PathVariable Long postId) {
        List<FileMetadataDto> fileDataDtos = postFileService.getPostFiles(postId);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, fileDataDtos));
    }

    /**
     * 함수명 : createPost()
     * 게시글을 생성하는 메서드
     */
    @PostMapping()
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> createPost(@AuthenticationPrincipal CustomUserDetails user,
                                                               @Valid @RequestBody PostCreateRequest postCreateRequest,
                                                               HttpServletRequest request
    ) {
        postService.createPost(postCreateRequest, request, user.getMember().getId());

        return ResponseEntity.status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED));
    }

    //게시글 생성,수정 - 파일
    /**
     * 함수명 : uploadPostFiles()
     * 게시글 생성시 파일을 등록하는 메서드
     */
    @PostMapping(value = "/{postId}/files", consumes = "multipart/form-data")
    public ResponseEntity<APIResponse<SuccessCode>> uploadPostFiles(
            @PathVariable Long postId,
            @RequestParam("files") List<MultipartFile> files) {

        //TODO) 토큰에서 접속자 정보 가져오기
        Long uploaderId = 1L;

        //파일 업로드
        postFileService.uploadPostFiles(files, postId, uploaderId);

        return ResponseEntity.status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED));
    }

    /**
     * 함수명 : updatePost()
     * 게시글을 수정하는 메서드
     */
    @PutMapping(value = "/{postId}")
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> updatePost(@AuthenticationPrincipal CustomUserDetails user,
                                                               @PathVariable Long postId,
                                                               @Valid @RequestBody PostUpdateRequest postUpdateRequest,
                                                               HttpServletRequest request
    ) {
        postService.updatePost(postUpdateRequest, request, user.getMember().getId());

        return ResponseEntity.status(SuccessCode.UPDATED.getStatus())
                .body(APIResponse.success(SuccessCode.UPDATED));
    }

    //게시물 수정 - 링크
    /**
     * 함수명 : deletePost()
     * 게시글을 삭제하는 메서드
     */
    @DeleteMapping("/{postId}")
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> deletePost(@AuthenticationPrincipal CustomUserDetails user,
                                                               @PathVariable Long postId,
                                                               HttpServletRequest request
    ) {
        postService.deletePost(postId, request, user.getMember().getId());
        return ResponseEntity.status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }

    //링크
    /**
     * 함수명 : uploadLinks()
     * 게시물에 링크를 등록하는 메서드(수정화면)
     */
    @PostMapping("/{postId}/links")
    public ResponseEntity<APIResponse<SuccessCode>> uploadLinks(@PathVariable Long postId,
                                                                @RequestBody List<LinkInput> linkInputs){

        //TODO)토큰으로 업로더 정보 가져오기
        Long uploaderId = 1L;

        postLinkService.uploadPostLinks(linkInputs, postId, uploaderId);

        return ResponseEntity.status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED));
    }

    /**
     * 함수명 : deleteLink()
     * 게시물의 링크를 단일 삭제하는 메서드(수정화면)
     */
    @DeleteMapping("/{postId}/links/{linkId}")
    public ResponseEntity<APIResponse<SuccessCode>> deleteLink(@PathVariable Long postId,
                                                               @PathVariable Long linkId){
        //TODO) 토큰으로 삭제수행자 정보 가져오기
        Long deleterId = 1L;

        postLinkService.deletePostLink(postId, linkId, deleterId);

        return ResponseEntity.status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }

    //게시물 수정 - 파일
    /**
     * 함수명 : deletePostFile()
     * 게시물의 파일을 단일 삭제하는 메서드(수정화면)
     */
    @DeleteMapping("/{postId}/files/{fileId}")
    public ResponseEntity<APIResponse<SuccessCode>> deletePostFiles(@PathVariable Long postId,
                                                                    @PathVariable Long fileId){
        //TODO) 토큰으로 삭제 수행자 정보 가져오기
        Long deleterId = 1L;

        postFileService.deletePostFile(postId, fileId, deleterId);

        return ResponseEntity.status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }
}