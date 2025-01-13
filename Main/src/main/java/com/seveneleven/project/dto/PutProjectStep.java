package com.seveneleven.project.dto;

import lombok.Getter;

import java.util.List;

public class PutProjectStep {

    @Getter
    public static class Request {
        private Long stepId;
        private String stepName;
        private String stepDescription;
        List<StepChecklist> checklists;
    }

    public static class StepChecklist {

    }

    @Getter
    public static class Response {

    }
}
