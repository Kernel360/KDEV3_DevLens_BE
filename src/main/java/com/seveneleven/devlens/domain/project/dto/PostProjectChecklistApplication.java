package com.seveneleven.devlens.domain.project.dto;

import com.seveneleven.devlens.domain.member.constant.YN;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

public class PostProjectChecklistApplication {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long checklistId;
        private String description;
        private List<File> files;
        private List<String> links;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Long projectId;
        private Long stepId;
        private Long checklistId;
        private String description;
        private String requesterId;
        private String requesterIp;
        private YN hasFile;
        private YN hasLink;
    }
}
