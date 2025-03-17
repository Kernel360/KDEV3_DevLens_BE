package com.seveneleven.entity.file.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LinkCategory {
    CHECK_APPROVE_REQUEST_LINK("승인 요청 첨부 링크"),
    CHECK_REJECTION_REASON_LINK("승인 요청 반려 사유 첨부 링크"),
    POST_ATTACHMENT_LINK("게시물 첨부 링크");

    private final String description;
}
