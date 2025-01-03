package com.seveneleven.devlens.domain.member.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BusinessType {
    INDIVIDUAL("개인사업자"),
    CORPORATION("법인사업자"),
    FREELANCER("프리랜서"),
    NON_PROFIT("비영리사업자"),
    FOREIGN("외국인사업자"),
    SMALL_BUSINESS("소기업/소상공인"),
    STARTUP("스타트업"),
    PROFESSIONAL("전문직 사업자");

    private final String description;
}
