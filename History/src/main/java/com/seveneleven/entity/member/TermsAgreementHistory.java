package com.seveneleven.entity.member;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.member.constant.YN;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "term_title", nullable = false)
    private String termTitle; // 약관 항목

    @Column(name = "member_id", nullable = false)
    private Long memberId; // 회원 ID

    @Column(name = "is_agree", nullable = false)
    @Enumerated(EnumType.STRING)
    private YN is_agree; // 약관 동의 여부


    // 생성 메서드
    public static TermsAgreementHistory createTermsAgreementHistory( Long termId, String termTitle, Long memberId, YN is_agree) {
        TermsAgreementHistory termsAgreeHistory = new TermsAgreementHistory();
        termsAgreeHistory.termId    = termId;
        termsAgreeHistory.termTitle = termTitle;
        termsAgreeHistory.memberId  = memberId;
        termsAgreeHistory.is_agree  = is_agree;
        return termsAgreeHistory;
    }

}