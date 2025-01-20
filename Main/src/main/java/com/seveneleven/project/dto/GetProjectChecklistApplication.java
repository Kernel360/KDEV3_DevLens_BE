package com.seveneleven.project.dto;

import com.seveneleven.entity.project.CheckRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetProjectChecklistApplication {

    public static class Response {
        Long id;
        String title;
        String content;
        List<File> files;
        List<String> links;

        private Response(Long id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
            files = new ArrayList<>();
            links = new ArrayList<>();
        }

        public static Response toDto(CheckRequest checkRequest) {
            return new Response(checkRequest.getId(), checkRequest.getTitle(), checkRequest.getContent());
        }
    }
}
