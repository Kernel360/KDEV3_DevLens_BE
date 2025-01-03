package com.seveneleven.devlens.domain.member.entity;

import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Enumerated(EnumType.STRING)
    private YN isRequired; // 필수 여부


    // 생성 메서드
    public static Term createTerm(String title, String content, YN isRequired) {
        Term term = new Term();
        term.title      = title;
        term.content    = content;
        term.isRequired = isRequired != null? isRequired : YN.N;
        return term;
    }

    // 삭제 메서드
    public void delete() {

    }
}