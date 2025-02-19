package com.seveneleven.project.dto;

import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.constant.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class GetProjectAuthorization {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long projectId;
        private List<CustomerMemberAuthorization> customerMemberAuthorizations;
        private List<DeveloperMemberAuthorization> developerMemberAuthorizations;

        @Override
        public String toString() {
            return "Response{" +
                    "projectId=" + projectId +
                    '}';
        }

        private Response(Long projectId, List<ProjectAuthorization> projectAuthorizations) {
            this.projectId = projectId;
            this.customerMemberAuthorizations = new ArrayList<>();
            this.developerMemberAuthorizations = new ArrayList<>();
            projectAuthorizations.forEach(authorization -> {
                    if(MemberType.CLIENT.equals(authorization.getMemberType())) {
                        customerMemberAuthorizations.add(new CustomerMemberAuthorization(authorization));
                    } else {
                        developerMemberAuthorizations.add(new DeveloperMemberAuthorization(authorization));
                    }
            });
        }

        public static Response toDto(Long projectId, List<ProjectAuthorization> projectAuthorizations) {
            return new Response(projectId, projectAuthorizations);
        }
    }

    @Getter
    public static class CustomerMemberAuthorization {
        private Long memberId;
        private String memberName;
        private String projectAuthorization;

        @Override
        public String toString() {
            return "CustomerMemberAuthorization{" +
                    "memberId=" + memberId +
                    '}';
        }

        private CustomerMemberAuthorization(ProjectAuthorization projectAuthorization) {
            this.memberId = projectAuthorization.getMember().getId();
            this.memberName = projectAuthorization.getMember().getName();
            this.projectAuthorization = projectAuthorization.getAuthorization();
        }

        public static CustomerMemberAuthorization toDto(ProjectAuthorization projectAuthorization) {
            return new CustomerMemberAuthorization(projectAuthorization);
        }
    }

    @Getter
    public static class DeveloperMemberAuthorization {
        private Long memberId;
        private String memberName;
        private String projectAuthorization;

        @Override
        public String toString() {
            return "DeveloperMemberAuthorization{" +
                    "memberId=" + memberId +
                    '}';
        }

        private DeveloperMemberAuthorization(ProjectAuthorization projectAuthorization) {
            this.memberId = projectAuthorization.getMember().getId();
            this.memberName = projectAuthorization.getMember().getName();
            this.projectAuthorization = projectAuthorization.getAuthorization();
        }

        public static DeveloperMemberAuthorization toDto(ProjectAuthorization projectAuthorization) {
            return new DeveloperMemberAuthorization(projectAuthorization);
        }
    }
}
