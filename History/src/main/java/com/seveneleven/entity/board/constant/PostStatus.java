package com.seveneleven.entity.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {

    DEFAULT("선택"),
    IN_PROGRESS("진행"),
    ADDITION("추가"),
    COMPLETED("완료"),
    ON_HOLD("보류");

    private final String description;

    // 사용할지 고민
    // FEEDBACK("피드백"),
    // REQUESTED("요청");
}
