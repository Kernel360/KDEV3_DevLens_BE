package com.seveneleven.board.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteCommentRequest {

    private Long modifierId;
    private String modifierIp;
}
