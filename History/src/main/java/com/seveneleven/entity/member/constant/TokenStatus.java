package com.seveneleven.entity.member.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenStatus {
    ACTIVE,
    BLACKLISTED
}
