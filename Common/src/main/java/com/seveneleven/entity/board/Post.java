package com.seveneleven.entity.board;

import com.seveneleven.entity.board.constant.PostStatus;
import com.seveneleven.entity.board.constant.TaskPriority;
import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.project.ProjectStep;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class Post extends BaseEntity {

    /*
        id : 게시물 ID (문서번호)
        projectStep : 프로젝트 단계 ID
        parentPost : 부모 게시물 ID
        ref : 게시글 그룹 구분
        refOrder : 게시글 그룹 순서
        childPostNum : 자식 게시물 개수
        priority : 우선순위 (DEFAULT, LOW, MEDIUM, HIGH)
        status : 상태 (DEFAULT, IN_PROGRESS, ADDITION, COMPLETED, ON_HOLD)
        title : 제목
        content : 내용
        writer : 작성자 이름
        deadline : 마감일자
        registerIp : 등록자 IP
        modifierIp : 수정자 IP
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "project_step_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectStep projectStep;

    @JoinColumn(name = "parent_post_id", nullable = true, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post parentPost;

    @Column(name = "ref")
    private Long ref;

    @Column(name = "ref_order")
    private Integer refOrder;

    @Column(name = "child_post_num")
    private Integer childPostNum;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority; // 우선순위 (DEFAULT, LOW, MEDIUM, HIGH)

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PostStatus status; // 게시물 상태 종류(DEFAULT, IN_PROGRESS, ADDITION, COMPLETED, ON_HOLD

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "writer", nullable = false)
    private String writer;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "register_ip", length = 50)
    private String registerIp;

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp;

    // 게시글 생성
    public static Post createPost(
            ProjectStep projectStep,
            Post parentPost,
            Long ref,
            Integer refOrder,
            TaskPriority priority,
            PostStatus status,
            String title,
            String content,
            String writer,
            LocalDate deadline,
            String registerIp
    ) {
        Post post = new Post();
        post.projectStep = projectStep;
        post.parentPost = parentPost;
        post.ref = ref;
        post.refOrder = refOrder;
        post.childPostNum = 0;
        post.priority = priority;
        post.status = status;
        post.title = title;
        post.content = content;
        post.writer = writer;
        post.deadline = deadline;
        post.registerIp = registerIp;
        post.modifierIp = null;
        return post;
    }

    // 게시글 수정
    public void updatePost(
            TaskPriority priority,
            PostStatus status,
            String title,
            String content,
            LocalDate deadline,
            String modifierIp
    ) {
        this.priority = priority;
        this.status = status;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.modifierIp = modifierIp;
    }

    // 답글 생성 시 child_post_num 증가
    public void increaseChildPostNum() {
        childPostNum++;
    }
    // 답글 삭제 시 child_post_num 감소
    public void decreaseChildPostNum() {
        childPostNum--;
    }

    public String getParentPostTitle() {
        return parentPost.getTitle();
    }

    public Long getParentPostId() {
        return parentPost.getId();
    }

    public boolean isParent() {
        return parentPost != null;
    }
}