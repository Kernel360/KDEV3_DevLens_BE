package com.seveneleven.devlens.domain.member.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TermsStatus {
    ACTIVE("활성화"),       // 약관이 현재 사용 중
    INACTIVE("비활성화"),   // 약관이 사용 중지됨
    DEPRECATED("폐기됨"),   // 약관이 더 이상 유효하지 않음
    EXPIRED("만료됨"),      // 약관이 유효 기간이 지나 만료된 상태
    DRAFT("초안");          // 약관이 아직 승인되지 않음

    private final String description;
}
