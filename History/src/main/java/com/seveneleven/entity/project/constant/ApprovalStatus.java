package com.seveneleven.entity.project.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApprovalStatus {
    WAITING("승인대기"),
    APPROVED("승인"),
    REJECTED("반려");

    private final String description;
}