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
@Table(name = "question")
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId; // 질문 ID

    @JoinColumn(name = "post_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Post postId; // 게시물 ID

    @Column(name = "question_content", nullable = false, columnDefinition = "TEXT")
    private String questionContent; // 질문 내용

    @Column(name = "answer_content", columnDefinition = "TEXT")
    private String answerContent; // 답변 내용

    @CreatedDate
    @Column(name = "registered_date", nullable = false, updatable = false)
    private LocalDateTime registeredDate; // 등록일시

    @LastModifiedDate
    @Column(name = "modification_date")
    private LocalDateTime modificationDate; // 수정일시

    @CreatedBy
    @JoinColumn(name = "register_id", updatable = false, referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member registerId; // 등록자

    @LastModifiedBy
    @JoinColumn(name = "modified_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member modifierId; // 수정자

    @Column(name = "register_ip", length = 50)
    private String registerIp; // 등록자 IP

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp; // 수정자 IP
}