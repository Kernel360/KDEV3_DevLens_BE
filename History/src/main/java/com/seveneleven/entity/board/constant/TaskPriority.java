package com.seveneleven.entity.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskPriority {
    DEFAULT("기본"),
    LOW("낮음"),
    MEDIUM("중간"),
    HIGH("높음");

    private final String description;
}
