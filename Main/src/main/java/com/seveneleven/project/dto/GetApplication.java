package com.seveneleven.project.dto;

import com.seveneleven.entity.project.CheckRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GetApplication {

    @Getter
    @NoArgsConstructor
    public static class Response {
        Long id;
        String title;
        String content;

        @Override
        public String toString() {
            return "Response{" +
                    "id=" + id +
                    '}';
        }

        private Response(Long id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }

        public static Response toDto(CheckRequest checkRequest) {
            return new Response(checkRequest.getId(), checkRequest.getTitle(), checkRequest.getContent());
        }
    }
}
