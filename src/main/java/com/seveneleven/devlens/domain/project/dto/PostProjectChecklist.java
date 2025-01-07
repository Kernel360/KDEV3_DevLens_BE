package com.seveneleven.devlens.domain.project.dto;

import com.seveneleven.devlens.global.entity.YesNo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

public class PostProjectChecklist {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String checklistName;
        private String checklistDescription;
        private List<File> files;
        private List<String> links;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long projectChecklistId;
        private String checklistName;
        private String checklistDescription;
        private YesNo isActive;
        private YesNo isChecked;
    }
}
