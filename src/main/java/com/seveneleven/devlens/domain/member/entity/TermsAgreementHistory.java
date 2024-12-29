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
@Table(name = "terms_agreement_history")
public class TermsAgreementHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 약관 동의 이력 ID

    @Column(name = "terms_title", nullable = false)
    private Long termsTitle; // 약관 항목

    @Column(name = "member_email", nullable = false)
    private Long memberEmail; // 회원 ID

    @Column(name = "agreement_status", nullable = false)
    private Boolean agreementStatus; // 약관 동의 여부

    @Column(name = "status", length = 50)
    private String status; // 상태
}