package com.seveneleven.board.service;

import com.seveneleven.board.dto.*;
import com.seveneleven.entity.board.constant.PostAction;
import com.seveneleven.board.repository.PostHistoryRepository;
import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.board.PostHistory;
import com.seveneleven.entity.board.constant.PostFilter;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import com.seveneleven.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.util.List;

import static com.seveneleven.board.dto.PostResponse.getPostResponse;
import static com.seveneleven.response.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostHistoryRepository postHistoryRepository;
    private final ProjectStepRepository projectStepRepository;
    private final MemberRepository memberRepository;
    private final PostFileService postFileService;

    private final CommentService commentService;

    private final int PAGE_SIZE = 10;

    /**
     * 함수명 : selectList()
     * 함수 목적 : 게시글 목록을 조회하는 메서드
     * param : 프로젝트 단계 ID, 현재 페이지 수, 입력 키워드, 필터
     */
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PostListResponse> selectPostList(Long projectStepId, Integer page, String keyword, PostFilter filter) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Order.desc("ref"), Sort.Order.asc("refOrder")));

        // todo : 필터 검색 기능 추가 예정
        Page<Post> repoPostList = postRepository.findAllByProjectStepId(projectStepId, pageable);

        Page<PostListResponse> postListResponsePage = repoPostList.map(post -> {
            Member member = memberRepository.findById(post.getCreatedBy())
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));
            return PostListResponse.toDto(post, member.getName());
        });

        return PageResponse.createPageResponse(postListResponsePage);
    }

    /**
     * 함수명 : selectPost()
     * 함수 목적 : 게시글 상세를 조회하는 메서드
     * param : 게시물 ID
     */
    @Transactional(readOnly = true)
    @Override
    public PostResponse selectPost(Long postId) throws Exception {
        Post post = getPost(postId);

        String memberName = memberRepository.findNameById((post.getCreatedBy()))
                .orElseThrow(() -> new BusinessException(NOT_FOUND_WRITER));

        Long parentPostId = null;
        if (post.getParentPost() != null) {
            parentPostId = post.getParentPost().getId();
        }

        // 댓글 목록 조회
        List<GetCommentResponse> comments = commentService.selectCommentList(postId);

        return getPostResponse(post, parentPostId, memberName, comments);
    }

    /**
     * 함수명 : createPost()
     * 함수 목적 : 게시글을 생성하는 메서드
     */
    @Transactional
    @Override
    public void createPost(PostCreateRequest postCreateRequest, List<MultipartFile> files) throws Exception {
        // todo : 작성권한 확인 로직 추가 예정

        // 원글인 경우
        if (postCreateRequest.getParentPostId() == null) {
            Post post = getCreatePost(postCreateRequest, postRepository.findMaxRef() + 1, 0);
            postRepository.save(post);
            //게시물 파일 업로드
            postFileService.uploadPostFile(files, post.getId(), post.getCreatedBy());
            postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.CREATE));

            // todo: 링크 저장 로직 추가 예정
        } else {
        // 답글인 경우
            if(!matchesProjectStepParentAndChild(postCreateRequest.getProjectStepId(), postCreateRequest.getParentPostId())) {
                throw new BusinessException(NOT_MATCH_PROJECTSTEPID);
            }
            Post post = getCreatePost(postCreateRequest, getRef(postCreateRequest.getParentPostId()), getRefOrder(postCreateRequest.getParentPostId())+1);
            postRepository.save(post);
            //게시물 파일 업로드
            postFileService.uploadPostFile(files, post.getId(), post.getCreatedBy());
            postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.CREATE));

            // 원글의 Child_Post_Num 컬럼값에 +1
            increaseChildPostNum(postCreateRequest.getParentPostId());

            // todo: 링크 저장 로직 추가 예정
        }
    }

    /**
     * 함수명 : updatePost()
     * 함수 목적 : 게시글을 수정하는 메서드
     */
    @Transactional
    @Override
    public void updatePost(PostUpdateRequest postUpdateRequest, List<MultipartFile> files) throws Exception {
        // 게시물 존재 여부 및 작성자 일치 여부 확인
        Post post = getPost(postUpdateRequest.getPostId());
        matchPostWriter(post.getCreatedBy(), postUpdateRequest.getModifierId());

        // Post 테이블에서 기존 게시글 수정
        post.updatePost(
                postUpdateRequest.getIsPinnedPost(),
                postUpdateRequest.getPriority(),
                postUpdateRequest.getStatus(),
                postUpdateRequest.getTitle(),
                postUpdateRequest.getContent(),
                postUpdateRequest.getDeadline(),
                postUpdateRequest.getModifierIp()
        );

        postRepository.save(post);
        //파일 수정
        postFileService.updatePostFiles(post.getId(), files);

        postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.UPDATE));

    }

    /**
     * 함수명 : deletePost()
     * 함수 목적 : 게시글을 삭제하는 메서드
     * param : 게시글 ID, 등록자 ID
     */
    @Transactional
    public void deletePost(Long postId, Long registeredId) throws Exception {
        // 게시물 존재 여부 및 작성자 일치 여부 확인
        Post post = getPost(postId);
        matchPostWriter(post.getCreatedBy(), registeredId);

        // 원글인 경우
        if(getParentPost(post) == null) {
            if(post.getChildPostNum() >= 1) {
                // 답글이 한 개 이상 존재하는 경우, 삭제 실패
                throw new BusinessException(NOT_DELETE_PARENT_POST);
            }
        } else {
            // 답글인 경우
            Post parentPost = getParentPost(post);
            decreaseChildPostNum(parentPost.getId());
        }
        //게시물 파일 일괄 삭제
        postFileService.deleteAllPostFiles(post.getId(), registeredId);
        postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.DELETE));
        postRepository.delete(post);
    }

    /**
     * 함수명 : getParentPost()
     * 함수 목적 : 부모게시물 여부 확인 메서드
     */
    private Post getParentPost(Post post) throws Exception {
        if(post.getParentPost() != null) {
            return postRepository.findById(post.getParentPost().getId())
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        }
        return null;
    }

    /**
     * 함수명 : getPost()
     * 함수 목적 : 게시물 존재 여부 확인 메서드
     */
    private Post getPost(Long postId) throws Exception {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }

    /**
     * 함수명 : matchPostWriter()
     * 함수 목적 : 게시물 작성자 일치 여부 확인 메서드
     */
    private void matchPostWriter(Long createdBy, Long modifierId) throws Exception {
        if(!createdBy.equals(modifierId)) {
            throw new BusinessException(NOT_MATCH_WRITER);
        }
    }

    /**
     * 함수명 : getCreatePost()
     * 함수 목적 : (원글,답글) 게시글 생성 메서드
     */
    private Post getCreatePost(PostCreateRequest postCreateRequest, Long ref, Integer refOrder) throws Exception {
        // 프로젝트 단계 확인
        ProjectStep projectStep = projectStepRepository.findById(postCreateRequest.getProjectStepId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_PROJECT_STEP));
        // 부모게시글 확인
        Post parentPost = null;
        if(postCreateRequest.getParentPostId() != null) {
            parentPost = postRepository.findById(postCreateRequest.getParentPostId())
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_PROJECT_STEP));
        }

        return Post.createPost(
                projectStep,
                parentPost,
                ref,
                refOrder,
                0,                      // CHILD_POST_NUM = 0
                postCreateRequest.getIsPinnedPost(),
                postCreateRequest.getPriority(),
                postCreateRequest.getStatus(),
                postCreateRequest.getTitle(),
                postCreateRequest.getContent(),
                postCreateRequest.getDeadline(),
                postCreateRequest.getRegisterIp(),
                postCreateRequest.getRegisterIp()
        );
    }

    /**
     * 함수명 : matchesProjectStepParentAndChild()
     * 함수 목적 : (답글) 부모게시글과 자식게시글의 프로젝트 단계 일치 여부 확인 메서드
     */
    private boolean matchesProjectStepParentAndChild(Long childProjectStepId, Long childParentPostId) throws Exception {
        // 부모게시글의 프로젝트 단계 값 받기
        Post post = postRepository.findById(childParentPostId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        return post.getProjectStep().getId().equals(childProjectStepId);
//        return Objects.equals(childProjectStepId, (postRepository.findById(childParentPostId).get()).getProjectStep().getId());
    }

    /**
     * 함수명 : getRef()
     * 함수 목적 : (답글) 원글의 REF 반환 메서드
     */
    private Long getRef(Long parentPostId) throws Exception {
        Post post = postRepository.findById(parentPostId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        if (post.getRef() == null) {
            throw new BusinessException(NOT_FOUND_POST);
        }
        return  post.getRef();
    }

    /**
     * 함수명 : getRefOrder()
     * 함수 목적 : (답글) 동일 그룹의 REF_ORDER 최대값 반환 메서드
     */
    private Integer getRefOrder(Long parentPostId) throws Exception {
        if(postRepository.findById(parentPostId).orElseThrow(() -> new BusinessException(NOT_FOUND_POST)).getChildPostNum().equals(0)) {
            return 0;
        }
        return postRepository.findMaxRefOrderByParentPostId(parentPostId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }

    /**
     * 함수명 : increaseChildPostNum()
     * 함수 목적 : 답글 생성 시 child_post_num 증가
     */
    private void increaseChildPostNum(Long parentPostId) throws Exception {
        Post post = postRepository.findById(parentPostId).orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        post.increaseChildPostNum();
        postRepository.save(post);
    }

    /**
     * 함수명 : decreaseChildPostNum()
     * 함수 목적 : 답글 삭제 시 child_post_num 감소
     */
    private void decreaseChildPostNum(Long parentPostId) throws Exception {
        Post post = postRepository.findById(parentPostId).orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        post.decreaseChildPostNum();
        postRepository.save(post);
    }
}
