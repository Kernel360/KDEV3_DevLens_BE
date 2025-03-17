package com.seveneleven.entity.file.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileHistoryType {
    REGISTER("파일 등록 이력"),
    DELETE("파일 삭제 이력");

    private final String description;
}
