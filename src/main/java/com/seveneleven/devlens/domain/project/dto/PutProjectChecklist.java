package com.seveneleven.devlens.domain.project.dto;

import com.seveneleven.devlens.domain.member.constant.YN;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PutProjectChecklist {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long checklistId;
        private String name;
        private String description;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private String description;
        private YN isChecked;
    }
}
