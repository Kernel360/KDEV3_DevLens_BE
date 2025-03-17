package com.seveneleven.entity.board;

import com.seveneleven.entity.board.constant.HistoryAction;
import com.seveneleven.entity.board.constant.PostStatus;
import com.seveneleven.entity.board.constant.TaskPriority;
import com.seveneleven.entity.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_history")
public class PostHistory extends BaseEntity {

    /*
        id : 게시물 이력 ID
        projectStepId : 프로젝트 단계 ID
        parentPostId : 부모 게시물 ID
        postId : 게시물 ID
        priority : 우선순위 (DEFAULT, LOW, MEDIUM, HIGH)
        status : 상태 (DEFAULT, IN_PROGRESS, ADDITION, COMPLETED, ON_HOLD)
        title : 제목
        content : 내용
        writer : 작성자 이름
        deadline : 마감일자
        action : 작업 종류 (CREATE, UPDATE, DELETE)
        registerIp : 등록자 IP
        modifierIp : 수정자 IP
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "project_step_id", nullable = false)
    private Long projectStepId;

    @Column(name = "parent_post_id")
    private Long parentPostId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "priority")
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

    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private HistoryAction action; // 작업 종류 (CREATE, UPDATE, DELETE)

    @Column(name = "registered_ip", length = 50)
    private String registeredIp; // 등록자 IP

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp; // 수정자 IP

    // 게시글 이력 생성
    public static PostHistory createPostHistory(Post post, HistoryAction action, String ip) {
        PostHistory postHistory = new PostHistory();
        postHistory.projectStepId = post.getProjectStep().getId();
        postHistory.parentPostId = postHistory.getParentPostId();
        postHistory.postId = post.getId();
        postHistory.priority = post.getPriority();
        postHistory.status = post.getStatus();
        postHistory.title = post.getTitle();
        postHistory.content = post.getContent();
        postHistory.writer = post.getWriter();
        postHistory.deadline = post.getDeadline();
        postHistory.action = action;
        postHistory.registeredIp = ip;
        postHistory.modifierIp = ip;

        return postHistory;
    }

    private Long getParentPostId() {
        if(parentPostId == null) {
            return null;
        }
        return parentPostId;
    }
}
