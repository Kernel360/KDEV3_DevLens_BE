package com.seveneleven.devlens.domain.project.dto;

import com.seveneleven.devlens.domain.member.constant.YN;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

public class PostProjectChecklist {

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
        private YN isActive;
        private YN isChecked;
    }
}
