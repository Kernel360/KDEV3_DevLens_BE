package com.seveneleven.devlens.domain.member.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessType {
    INDIVIDUAL("개인사업자"),
    CORPORATION("법인사업자");

    private final String description;
}
