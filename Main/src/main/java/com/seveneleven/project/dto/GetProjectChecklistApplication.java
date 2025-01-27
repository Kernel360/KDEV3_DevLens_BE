package com.seveneleven.project.dto;

import com.seveneleven.entity.project.CheckRequest;
import lombok.NoArgsConstructor;

public class GetProjectChecklistApplication {

    @NoArgsConstructor
    public static class Response {
        Long id;
        String checkRequestTitle;
        String checkRequestContent;

        private Response(CheckRequest checkRequest) {
            this.id = checkRequest.getId();
            this.checkRequestTitle = checkRequest.getTitle();
            this.checkRequestContent = checkRequest.getContent();
        }

        public static Response toDto(CheckRequest checkRequest) {
            return new Response(checkRequest);
        }
    }
}
