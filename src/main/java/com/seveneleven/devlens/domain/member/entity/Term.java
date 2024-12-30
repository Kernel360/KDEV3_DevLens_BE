package com.seveneleven.devlens.domain.member.entity;

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
@Table(name = "term")
public class Term extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 약관 이력 ID

    @Column(name = "title", nullable = false, length = 255)
    private String title; // 약관 항목

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content; // 약관 내용

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired; // 필수 여부
}