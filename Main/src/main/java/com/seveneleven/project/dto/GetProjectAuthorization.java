package com.seveneleven.project.dto;

import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.constant.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class GetProjectAuthorization {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long stepId;
        private List<MemberAuthorization> memberAuthorization;

        private Response(Long stepId, List<ProjectAuthorization> projectAuthorizations) {
            this.stepId = stepId;
            memberAuthorization = projectAuthorizations.stream().map(MemberAuthorization::toDto).toList();
        }

        public static Response toDto(Long stepId, List<ProjectAuthorization> projectAuthorizations) {
            return new Response(stepId, projectAuthorizations);
        }
    }

    @Getter
    public static class MemberAuthorization {
        private Long memberId;
        private String projectAuthorization;
        private MemberType memberDivision;

        private MemberAuthorization(ProjectAuthorization projectAuthorization) {
            this.memberId = projectAuthorization.getMember().getId();
            this.projectAuthorization = projectAuthorization.getAuthorizationCode();
            this.memberDivision = projectAuthorization.getMemberType();
        }

        public static MemberAuthorization toDto(ProjectAuthorization projectAuthorization) {
            return new MemberAuthorization(projectAuthorization);
        }
    }
}
