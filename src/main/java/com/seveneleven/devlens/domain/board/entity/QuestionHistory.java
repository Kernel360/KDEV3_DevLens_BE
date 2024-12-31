package com.seveneleven.devlens.domain.board.entity;

import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "question_history")
public class QuestionHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_history_id")
    private Long questionHistoryId; // 질문 이력 ID

    @Column(name = "post_id", nullable = false)
    private Long postId; // 게시물 ID

    @Column(name = "question_id", nullable = false)
    private Long questionId; // 질문 ID

    @Column(name = "question_content", nullable = false, columnDefinition = "TEXT")
    private String questionContent; // 질문 내용

    @CreatedBy
    @Column(name = "register_id", updatable = false)
    private Long registerId; // 등록자 ID

    @Column(name = "register_ip", length = 50)
    private String registerIp; // 등록자 IP

    @CreatedDate
    @Column(name = "registered_date", nullable = false, updatable = false)
    private LocalDateTime registeredDate; // 등록일시

    @LastModifiedBy
    @Column(name = "modifier_id")
    private Long modifierId; // 수정자 ID

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp; // 수정자 IP

    @LastModifiedDate
    @Column(name = "modificated_date")
    private LocalDateTime modificatedDate; // 수정일시


}