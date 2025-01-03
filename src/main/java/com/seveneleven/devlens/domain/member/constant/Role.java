package com.seveneleven.devlens.domain.member.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    ADMIN("관리자"),
    SUPER("대표 회원"),
    USER("일반 회원");

    private final String description;
}
