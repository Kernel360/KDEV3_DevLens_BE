package com.seveneleven.devlens.domain.member.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    SUSPENDED("정지");

    private final String description;
}
