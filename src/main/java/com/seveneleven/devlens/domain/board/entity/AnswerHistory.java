package com.seveneleven.devlens.domain.board.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "answer_history")
public class AnswerHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_history_id")
    private Long questionHistoryId;

    @Column(name = "question_id") // 질문 ID를 기본 키로 사용
    private Long questionId;

    @Column(name = "content", nullable = false)
    private String content; // 답변 내용

    @Column(name = "register_id", nullable = false)
    private String registerId; // 답변 등록자

    @Column(name = "register_ip", nullable = false)
    private String registerIp; // 답변 등록자 IP

    @CreatedDate
    @Column(name = "registered_date", nullable = false)
    private LocalDateTime registeredDate;
}
