package com.seveneleven.board.service;

import com.seveneleven.board.dto.*;
import com.seveneleven.entity.board.constant.HistoryAction;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.board.constant.PostFilter;
import com.seveneleven.entity.board.constant.PostSort;
import com.seveneleven.entity.member.constant.Role;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.service.MemberService;
import com.seveneleven.response.PaginatedResponse;
import com.seveneleven.util.GetIpUtil;
import com.seveneleven.util.file.dto.FileMetadataResponse;
import com.seveneleven.util.file.dto.LinkInput;
import com.seveneleven.util.file.dto.LinkResponse;
import com.seveneleven.util.security.dto.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.seveneleven.board.dto.PostResponse.getPostResponse;
import static com.seveneleven.response.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostFileService postFileService;
    private final MemberService memberService;
    private final CommentService commentService;
    private final PostLinkService postLinkService;
    private final PostReader postReader;
    private final PostStore postStore;

    private final GetIpUtil getIpUtil;

    private final int PAGE_SIZE = 10;

    /**
     * 함수명 : selectPostList()
     * 함수 목적 : 게시글 목록을 조회하는 메서드
     * param : 프로젝트 단계 ID, 현재 페이지 수, 입력 키워드, 필터, 정렬 타입
     */
    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<PostListResponse> selectPosts(Boolean isAllStages, Long projectId, Long projectStepId, Integer page, String keyword, PostFilter filter, PostSort sortType) {
        String repoFilter = null;
        if(filter != null) {
            repoFilter = filter.name();
        }
        if(keyword != null) {
            keyword = keyword.trim();
        }
        List<Long> projectStepIds = postReader.getProjectStepsId(projectId);

        Page<PostListResponse> repoPostList;
        if(isAllStages) {
            repoPostList = postReader.getAllPostsNoStepId(projectStepIds, keyword, repoFilter, PageRequest.of(page, PAGE_SIZE, getSort(isAllStages, sortType)));
        } else {
            repoPostList = postReader.getPostsByProjectStepId(projectStepId, keyword, repoFilter, PageRequest.of(page, PAGE_SIZE, getSort(isAllStages, sortType)));
        }
        repoPostList.getContent().forEach(post ->
            post.setFileExist(!postFileService.getPostFiles(post.getPostId()).isEmpty())
        );

        return PaginatedResponse.createPaginatedResponse(repoPostList);
    }

    /**
     * 함수명 : selectPost()
     * 함수 목적 : 게시글 상세를 조회하는 메서드
     * param : 게시물 ID
     */
    @Transactional(readOnly = true)
    @Override
    public PostResponse selectPost(Long postId, Long userId) {
        Post post = postReader.getPost(postId);

        // 작성자 프로필 이미지 조회
        String writerImage = memberService.getProfileImageUrl(post.getCreatedBy());

        // 댓글 목록 조회
        List<GetCommentDetailResponse> comments = commentService.selectCommentList(post.getId(), userId);

        // 링크 목록 조회
        List<LinkResponse> links = postLinkService.getPostLinks(post.getId());

        // 파일 목록 조회
        List<FileMetadataResponse> files = postFileService.getPostFiles(post.getId());

        // 관련 게시글 조회
        List<RelatedPostResponse> relatedPosts = new ArrayList<>();

        if(post.isParent()) {
            relatedPosts = postReader.getRelatedPosts(postId);
        }
        if(!post.isParent()) {
            relatedPosts.add(RelatedPostResponse.getRelatedPostResponse(post.getParentPostId(), post.getParentPostTitle()));
        }

        return getPostResponse(
                post,
                postReader.getWriter(post.getCreatedBy()),
                writerImage,
                userId.equals(post.getCreatedBy()),
                comments,
                links,
                files,
                post.isParent(),
                relatedPosts
        );
    }

    /**
     * 함수명 : createPost()
     * 함수 목적 : 게시글을 생성하는 메서드
     */
    @Transactional
    @Override
    public Map<String, Long> createPost(PostCreateRequest postCreateRequest, HttpServletRequest request, String registerName, String registerRole) {
        String registerIp = getIpUtil.getIpAddress(request);
        Post post;
        Long postId;

        // 원글인 경우
        if (postCreateRequest.getParentPostId() == null) {
            post = getCreatePost(
                    registerName,
                    postCreateRequest,
                    null,
                    registerIp,
                    postReader.getMaxRef() + 1,
                    0
            );
            postId = savePostAndPostHistory(post, registerIp, HistoryAction.CREATE);
        } else {
            // 답글인 경우
            if(!matchesProjectStepParentAndChild(postCreateRequest.getProjectStepId(), postCreateRequest.getParentPostId())) {
                throw new BusinessException(NOT_MATCH_PROJECTSTEPID);
            }

            Post parentPost = postReader.getPost(postCreateRequest.getParentPostId());

            post = getCreatePost(
                    registerName,
                    postCreateRequest,
                    parentPost,
                    registerIp,
                    parentPost.getRef(),
                    postReader.getRefOrder(parentPost) + 1
            );
            postId = savePostAndPostHistory(post, registerIp, HistoryAction.CREATE);
            parentPost.increaseChildPostNum();
        }

        // 요청 dto 에서 링크 리스트를 가져와서 게시물 링크 업로드
        uploadLink(postCreateRequest, post, registerRole);

        Map<String, Long> responseMap = new HashMap<>();
        responseMap.put("postId", postId);

        return responseMap;
    }

    /**
     * 함수명 : updatePost()
     * 함수 목적 : 게시글을 수정하는 메서드
     */
    @Transactional
    @Override
    public void updatePost(PostUpdateRequest postUpdateRequest, HttpServletRequest request, CustomUserDetails user) {
        // 게시물 존재 여부 및 작성자 일치 여부 확인
        Post post = postReader.getPost(postUpdateRequest.getPostId());
        checkPostEditPermission(post.getCreatedBy(), user);

        String modifierIp = getIpUtil.getIpAddress(request);
        // Post 테이블에서 기존 게시글 수정
        post.updatePost(
                postUpdateRequest.getPriority(),
                postUpdateRequest.getStatus(),
                postUpdateRequest.getTitle(),
                postUpdateRequest.getContent(),
                postUpdateRequest.getDeadline(),
                modifierIp
        );

        savePostAndPostHistory(post, modifierIp, HistoryAction.UPDATE);
    }

    /**
     * 함수명 : deletePost()
     * 함수 목적 : 게시글을 삭제하는 메서드
     * param : 게시글 ID, 등록자 ID
     */
    @Transactional
    public void deletePost(Long postId, HttpServletRequest request, CustomUserDetails user) {
        Post post = postReader.getPost(postId);
        checkPostEditPermission(post.getCreatedBy(), user);

        String deleterIp = getIpUtil.getIpAddress(request);

        // 원글인 경우
        if(post.getParentPost() == null) {
            if(post.getChildPostNum() >= 1) {
                // 답글이 한 개 이상 존재하는 경우, 삭제 실패
                throw new BusinessException(NOT_DELETE_PARENT_POST);
            }
        } else {
            // 답글인 경우
            post.getParentPost().decreaseChildPostNum();
        }
        // 해당 게시물의 댓글 일괄 삭제
        commentService.deleteAllComments(post, deleterIp);

        // 해당 게시물의 링크 일괄 삭제
        postLinkService.deleteAllPostLinks(postId, user.getId(), user.getRole().toString());

        // 게시물 파일 일괄 삭제
        postFileService.deleteAllPostFiles(post.getId(), user.getId(), user.getRole().toString());

        postStore.storePostHistory(post, HistoryAction.DELETE, deleterIp);
        postStore.deletePost(post);
    }

    /**
     * 함수명 : getSort()
     * 함수 목적 : 게시물 목록 조회 시 sortType 에 따른 정렬 기준 반환 메서드
     */
    private Sort getSort(Boolean isAllStages, PostSort sortType) {
        boolean isNewest = PostSort.NEWEST.equals(sortType);

        if (isAllStages) {
            return Sort.by(isNewest ? Sort.Order.desc("createdAt") : Sort.Order.asc("createdAt"));
        }

        return Sort.by(
                isNewest ? Sort.Order.desc("ref") : Sort.Order.asc("ref"),
                Sort.Order.asc("refOrder")
        );
    }

    /**
     * 함수명 : savePostAndPostHistory()
     * 함수 목적 : 게시글 및 게시글 이력 저장 메서드
     */
    private Long savePostAndPostHistory(Post post, String ip, HistoryAction postAction) {
        Post savedPost = postStore.storePost(post);
        postStore.storePostHistory(post, postAction, ip);

        return savedPost.getId();
    }

    /**
     * 함수명 : uploadLink()
     * 함수 목적 : 요청 dto 에서 링크 리스트를 가져와 게시물 링크를 업로드하는 메서드
     */
    private void uploadLink(PostCreateRequest postCreateRequest, Post post, String registerRole) {
        //요청 dto 에서 링크 리스트 가져오기
        List<LinkInput> linkInputs = postCreateRequest.getLinkInputList();
        //게시물 링크 업로드
        postLinkService.uploadPostLinks(linkInputs, post.getId(), post.getCreatedBy(), registerRole);
    }

    /**
     * 함수명 : matchPostWriter()
     * 함수 목적 : 게시물 작성자 또는 관리자 일치 여부 확인 메서드
     */
    private void checkPostEditPermission(Long createdBy, CustomUserDetails user) {
        if(!createdBy.equals(user.getId()) && !(Role.ADMIN.equals(user.getMember().getRole()))) {
            throw new BusinessException(NOT_HAVE_EDIT_PERMISSION);
        }
    }

    /**
     * 함수명 : getCreatePost()
     * 함수 목적 : (원글,답글) 게시글 생성 메서드
     */
    private Post getCreatePost(String registerName, PostCreateRequest postCreateRequest, Post parentPost, String registerIp, Long ref, Integer refOrder) {
        // 프로젝트 단계 확인
        ProjectStep projectStep = postReader.getProjectStep(postCreateRequest.getProjectStepId());

        return Post.createPost(
                projectStep,
                parentPost,
                ref,
                refOrder,
                postCreateRequest.getPriority(),
                postCreateRequest.getStatus(),
                postCreateRequest.getTitle(),
                postCreateRequest.getContent(),
                registerName,
                postCreateRequest.getDeadline(),
                registerIp
        );
    }

    /**
     * 함수명 : matchesProjectStepParentAndChild()
     * 함수 목적 : (답글) 부모게시글과 자식게시글의 프로젝트 단계 일치 여부 확인 메서드
     */
    private boolean matchesProjectStepParentAndChild(Long childProjectStepId, Long childParentPostId) {
        // 부모게시글의 프로젝트 단계 값 받기
        Post post = postReader.getPost(childParentPostId);
        return post.getProjectStep().getId().equals(childProjectStepId);
    }
}