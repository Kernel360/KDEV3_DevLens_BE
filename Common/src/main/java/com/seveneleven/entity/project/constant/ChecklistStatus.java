package com.seveneleven.entity.project.constant;

import lombok.Getter;

@Getter
public enum ChecklistStatus {
    APPLICATION_WAITING,
    APPROVE_WAITING,
    APPROVED,
    REJECTED,
    DELETED
}
