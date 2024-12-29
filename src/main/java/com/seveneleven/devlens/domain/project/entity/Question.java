package com.seveneleven.devlens.domain.project.entity;

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
    @Column(name = "id")
    private Long id; // 질문 ID

    @JoinColumn(name = "post_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Post postId; // 게시물 ID

    @Column(name = "question_content", nullable = false, columnDefinition = "TEXT")
    private String questionContent; // 질문 내용

    @Column(name = "answer_content", columnDefinition = "TEXT")
    private String answerContent; // 답변 내용

    @Column(name = "is_answered", nullable = false, length = 1)
    private String isAnswered; // 답변 여부 (Y, N)

    @CreatedDate
    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate; // 등록일시

    @LastModifiedDate
    @Column(name = "modification_date")
    private LocalDateTime modificationDate; // 수정일시

    @CreatedBy
    @Column(name = "registered_by", length = 100)
    private String registeredBy; // 등록자

    @LastModifiedBy
    @Column(name = "modified_by", length = 100)
    private String modifiedBy; // 수정자

    @Column(name = "registered_ip", length = 50)
    private String registeredIp; // 등록자 IP

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp; // 수정자 IP

    @Column(name = "answer_registered_ip", length = 50)
    private String answerRegisteredIp; // 답변 등록자 IP

    @Column(name = "answer_registered_date")
    private LocalDateTime answerRegisteredDate;

    @Column(name = "answer_modifier_ip", length = 50)
    private String answerModifierIp; // 답변 수정자 IP

    @Column(name = "answer_modified_date")
    private LocalDateTime answerModifiedDate;
}