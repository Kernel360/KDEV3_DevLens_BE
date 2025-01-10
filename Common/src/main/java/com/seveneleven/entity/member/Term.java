package com.seveneleven.entity.member;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.member.constant.TermsStatus;
import com.seveneleven.entity.member.constant.YN;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

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
    private YN isRequired = YN.N; // 필수 여부

    @Column(name = "expiration_date")
    private DateTime expiration_date; // 만료 일자

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private TermsStatus status; // 상태


    // 생성 메서드
    public static Term createTerm(String title, String content, YN isRequired, TermsStatus status) {
        Term term = new Term();
        term.title      = title;
        term.content    = content;
        term.isRequired = isRequired;
        term.status     = status;
        return term;
    }

    // 상태 변경 메서드
    public void updateStatus(TermsStatus status) {
        this.status = status;
    }
}