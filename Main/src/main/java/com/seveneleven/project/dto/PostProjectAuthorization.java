package com.seveneleven.project.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.constant.Authorization;
import com.seveneleven.entity.project.constant.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class PostProjectAuthorization {

    @Getter
    @NoArgsConstructor
    public static class Request {
        List<CustomerMemberAuthorization> customerAuthorizations;
        List<DeveloperMemberAuthorization> developerAuthorizations;
        // TODO - toString 추가
    }

    public interface MemberAuthorization {
        ProjectAuthorization toEntity(Project project, Member member);
    }

    @Getter
    @NoArgsConstructor
    public static abstract class BaseMemberAuthorization implements MemberAuthorization {
        protected Long memberId;
        protected MemberType memberType;
        protected Authorization projectAuthorization;

        public abstract ProjectAuthorization toEntity(Project project, Member member);

        @Override
        public String toString() {
            return "MemberAuthorization{" +
                    "memberId=" + memberId +
                    '}';
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CustomerMemberAuthorization extends BaseMemberAuthorization {
        protected MemberType memberType = MemberType.CLIENT;

        @Override
        public ProjectAuthorization toEntity(Project project, Member member) {
            return ProjectAuthorization.create(member, project, memberType, projectAuthorization);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class DeveloperMemberAuthorization extends BaseMemberAuthorization {
        protected MemberType memberType = MemberType.DEVELOPER;

        @Override
        public ProjectAuthorization toEntity(Project project, Member member) {
            return ProjectAuthorization.create(member, project, memberType, projectAuthorization);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long projectId;
        private List<FailList> failList;

        private Response(Long projectId) {
            this.projectId = projectId;
            this.failList = new ArrayList<>();
        }

        @Override
        public String toString() {
            return "Response{" +
                    "projectId=" + projectId +
                    '}';
        }

        public static Response create(Long projectId) {
            return new Response(projectId);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class FailList {
        private Long memberId;
        private MemberType memberType;
        private Authorization projectAuthorization;
        private HttpStatus status;
        private String message;

        private FailList(MemberAuthorization member, HttpStatus status, String message) {
            if(member instanceof BaseMemberAuthorization baseMemberAuthorization) {
                this.memberId = baseMemberAuthorization.getMemberId();
                this.memberType = baseMemberAuthorization.getMemberType();
                this.projectAuthorization = baseMemberAuthorization.getProjectAuthorization();
            }
            this.status = status;
            this.message = message;
        }

        @Override
        public String toString() {
            return "FailList{" +
                    "memberId=" + memberId +
                    '}';
        }

        public static FailList toDto(MemberAuthorization member, HttpStatus status, String message) {
            return new FailList(member, status, message);
        }
    }
}
