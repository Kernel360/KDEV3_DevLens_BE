package com.seveneleven.board.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentRequest {

    @Nullable
    private Long parentCommentId;

    @NotEmpty
    private String content;

    @NotBlank
    private String registerIp;
}
