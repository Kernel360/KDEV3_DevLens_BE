package com.seveneleven.devlens.domain.board.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "answer")
public class Answer extends BaseEntity {

    @Id
    @Column(name = "question_id") // 질문 ID를 기본 키로 사용
    private Long questionId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // 질문 ID를 외래 키이면서 기본 키로 매핑
    @JoinColumn(name = "question_id", nullable = false)
    private Question question; // 질문 엔티티 참조

    @Column(name = "content", nullable = false)
    private String content; // 답변 내용

    @Column(name = "register_id", nullable = false)
    private String registerId; // 답변 등록자

    @Column(name = "register_ip", nullable = false)
    private String registerIp; // 답변 등록자 IP
}