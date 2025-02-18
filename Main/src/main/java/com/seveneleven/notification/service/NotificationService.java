package com.seveneleven.notification.service;

import com.seveneleven.board.service.CommentReader;
import com.seveneleven.board.service.PostReader;
import com.seveneleven.entity.board.Comment;
import com.seveneleven.entity.board.Post;
import com.seveneleven.notification.controller.NotificationController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final PostReader postReader;
    private final CommentReader commentReader;

    // 메시지 알림
    public SseEmitter subscribe(Long userId) {
        // 1. sseEmitter 객체 생성
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        // 2. 연결
        try {
            sseEmitter.send(SseEmitter.event().name("connect"));
            log.info("NotificationService - sseEmitter 연결 성공");
        } catch (IOException e) {
            log.error("NotificationService - sseEmitter 연결시 에러 발생", e);
        }

        // 3. 저장
        NotificationController.sseEmitters.put(userId, sseEmitter);

        // 4. 연결 종료 (연결 완료, 연결 타임아웃, 연결 오류)
        sseEmitter.onCompletion(() -> NotificationController.sseEmitters.remove(userId));
        sseEmitter.onTimeout(() -> NotificationController.sseEmitters.remove(userId));
        sseEmitter.onError((e) -> NotificationController.sseEmitters.remove(userId));

        return sseEmitter;
    }

    // 댓글 알림
    public void notifyComment(Long postId) {
        Post post = postReader.getPost(postId);
        Long projectStepId = post.getProjectStep().getId();
        Comment comment = commentReader.getLatestComment(postId);
        Long writerId = post.getCreatedBy();

        if (NotificationController.sseEmitters.containsKey(writerId)) {
            SseEmitter sseEmitter = NotificationController.sseEmitters.get(writerId);
            try {
                Map<String, String> eventData = new HashMap<>();
                eventData.put("message", "댓글이 달렸습니다.");
                eventData.put("projectId", postReader.getProjectStep(projectStepId).getProject().getId().toString());
                eventData.put("projectStepId", projectStepId.toString());
                eventData.put("postId", postId.toString());
                eventData.put("writer", comment.getWriter());
                eventData.put("createdAt", comment.getCreatedAt().toLocalDate().toString());
                eventData.put("content", comment.getContent());

                log.info("게시글: {} 작성자: {}에게 댓글 알림 전송", postId, writerId);
                sseEmitter.send(SseEmitter.event().name("addComment").data(eventData));
            } catch (IOException e) {
                log.error("게시글: {} 작성자: {}에게 댓글 알림 전송 실패", postId, writerId, e);
                NotificationController.sseEmitters.remove(writerId);
            }
        } else {
            log.info("게시글: {} 작성자: {}가 SSE 구독하지 않음", postId, writerId);
        }
    }

}
