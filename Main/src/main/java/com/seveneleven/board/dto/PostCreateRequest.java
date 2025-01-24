package com.seveneleven.board.dto;

import com.seveneleven.entity.board.constant.PostStatus;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.util.file.dto.LinkInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PostCreateRequest {

    // todo: Dto validation 추가 예정

    @NotNull
    private Long projectStepId; // 프로젝트 단계 ID
    private Long parentPostId;  // 부모 게시물 ID
    private YesNo isPinnedPost;    // 상단고정여부
    private Integer priority;   // 우선순위
    private PostStatus status;  // 상태

    @NotBlank
    private String title;       // 제목

    @NotBlank
    private String content;     // 내용
    private LocalDate deadline; // 마감일자

    @NotNull
    private Long registerId;    // 등록자 ID

    @NotBlank
    private String registerIp;  // 등록자 IP

    @NotNull
    private LocalDateTime registerDate; // 등록일시

    @Size(min = 0)
    private List<LinkInput> linkInputList = new ArrayList<>(); //링크 목록

    public Long getParentPostId() {
        if(parentPostId == null) {
            return null;
        }
        return parentPostId;
    }

}
