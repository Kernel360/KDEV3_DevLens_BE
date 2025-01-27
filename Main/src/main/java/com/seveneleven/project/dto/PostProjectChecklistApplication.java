package com.seveneleven.project.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.Checklist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

public class PostProjectChecklistApplication {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String title;
        private String description;
        private Long requesterId;
        private List<File> files;
        private List<String> links;

        public CheckRequest createCheckRequest(Checklist checklist, Member requester, String requestIp) {
            return CheckRequest.create(checklist, title, description, requester, requestIp);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private Long checklistId;
        private String title;
        private String description;
        private Long requesterId;
        private String requesterIp;

        public Response(CheckRequest checkRequest) {
            this.id = checkRequest.getId();
            this.checklistId = checkRequest.getChecklist().getId();
            this.title = checkRequest.getTitle();
            this.description = checkRequest.getContent();
            this.requesterId = checkRequest.getRequester().getId();
            this.requesterIp = checkRequest.getRequestIp();
        }

        public static Response toDto(CheckRequest checkRequest) {
            return new Response(checkRequest);
        }
    }
}
