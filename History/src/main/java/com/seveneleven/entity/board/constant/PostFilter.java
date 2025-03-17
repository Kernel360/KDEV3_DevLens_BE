package com.seveneleven.entity.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostFilter {
    ALL("전체"),
    TITLE("제목"),
    CONTENT("내용"),
    WRITER("작성자"),
    DOC_NUM("문서번호");

    private final String description;
}
