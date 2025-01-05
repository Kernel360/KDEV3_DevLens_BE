package com.seveneleven.devlens.domain.board.dto;

import lombok.Getter;

@Getter
public enum PostStatus {

    DEFAULT("선택"),
    IN_PROGRESS("진행"),
    ADDITION("추가"),
    COMPLETED("완료"),
    ON_HOLD("보류");

    private final String description;

    PostStatus(String description) {
        this.description = description;
    }

    // 사용할지 고민
    // FEEDBACK("피드백"),
    // REQUESTED("요청");
}
