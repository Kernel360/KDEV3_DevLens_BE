package com.seveneleven.entity.file.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LinkHistoryType {
    REGISTER("링크 등록 이력"),
    DELETE("링크 삭제 이력");

    private final String description;
}
