package com.seveneleven.entity.project.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Authorization {
    APPROVER("승인권자"),
    PARTICIPANT("참가자");

    private final String description;
}
