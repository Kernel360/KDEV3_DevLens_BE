package com.seveneleven.entity.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HistoryAction {

    CREATE("생성"),
    UPDATE("수정"),
    DELETE("삭제");

    private final String description;
}
