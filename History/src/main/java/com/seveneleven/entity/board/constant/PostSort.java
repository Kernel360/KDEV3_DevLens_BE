package com.seveneleven.entity.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostSort {
    NEWEST("최신순"),
    OLDEST("오래된순");

    private final String description;
}
