package com.seveneleven.project.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.util.file.dto.LinkInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
        private List<LinkInput> linkInputs = new ArrayList<>();

        @Override
        public String toString() {
            return "Request{" +
                    "linkInputs=" + linkInputs +
                    '}';
        }

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

        @Override
        public String toString() {
            return "Response{" +
                    "id=" + id +
                    '}';
        }

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
