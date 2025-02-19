package com.seveneleven.project.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.constant.Authorization;
import com.seveneleven.entity.project.constant.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PatchAuthorization {

    public interface MemberAuthorization {
        ProjectAuthorization toEntity(Project project, Member member);
    }

    @Getter
    @NoArgsConstructor
    public static abstract class BaseMemberAuthorization implements MemberAuthorization {
        protected Long memberId;
        protected String memberName;
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

        CustomerMemberAuthorization(com.seveneleven.entity.project.ProjectAuthorization projectAuthorization) {
            this.memberId = projectAuthorization.getMember().getId();
            this.memberName = projectAuthorization.getMember().getName();
            this.projectAuthorization = projectAuthorization.getAuthorizationCode();
        }

        public static CustomerMemberAuthorization toDto(com.seveneleven.entity.project.ProjectAuthorization projectAuthorization) {
            return new CustomerMemberAuthorization(projectAuthorization);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class DeveloperMemberAuthorization extends BaseMemberAuthorization {
        protected MemberType memberType = MemberType.DEVELOPER;

        @Override
        public com.seveneleven.entity.project.ProjectAuthorization toEntity(Project project, Member member) {
            return com.seveneleven.entity.project.ProjectAuthorization.create(member, project, memberType, projectAuthorization);
        }

        DeveloperMemberAuthorization(com.seveneleven.entity.project.ProjectAuthorization projectAuthorization) {
            this.memberId = projectAuthorization.getMember().getId();
            this.memberName = projectAuthorization.getMember().getName();
            this.projectAuthorization = projectAuthorization.getAuthorizationCode();
        }

        public static DeveloperMemberAuthorization toDto(com.seveneleven.entity.project.ProjectAuthorization projectAuthorization) {
            return new DeveloperMemberAuthorization(projectAuthorization);
        }
    }
}
