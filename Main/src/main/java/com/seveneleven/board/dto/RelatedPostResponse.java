package com.seveneleven.board.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RelatedPostResponse {
    private Long id;
    private String title;

    private RelatedPostResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static RelatedPostResponse getRelatedPostResponse(Long id, String title) {
        return new RelatedPostResponse(id, title);
    }
}
