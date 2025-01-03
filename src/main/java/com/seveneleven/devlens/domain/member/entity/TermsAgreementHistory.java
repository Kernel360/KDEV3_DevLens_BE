package com.seveneleven.devlens.domain.member.entity;

import com.seveneleven.devlens.domain.member.constant.TermsStatus;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "terms_agreement_history")
public class TermsAgreementHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 약관 동의 이력 ID

    @Column(name = "term_id")
    private Long termId; // 약관 이력 ID

    @Column(name = "terms_title", nullable = false)
    private Long termsTitle; // 약관 항목

    @Column(name = "member_email", nullable = false)
    private Long memberEmail; // 회원 ID

    @Column(name = "agreement_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private YN agreementStatus; // 약관 동의 여부

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private TermsStatus status; // 상태
}