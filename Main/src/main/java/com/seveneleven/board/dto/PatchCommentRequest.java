package com.seveneleven.board.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchCommentRequest {

    // private Long postId; // Path로 받을지 말지 고민
    private String content;
    private Long modifierId;
    private String modifierIp;
}
