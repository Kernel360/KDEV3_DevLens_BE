package com.seveneleven.project.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.constant.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GetMemberAuthorization {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long memberId;
        private String authorization;
        private MemberType memberType;

        @Override
        public String toString() {
            return "Response{" +
                    "memberId=" + memberId +
                    '}';
        }

        private Response(ProjectAuthorization authorization) {
            this.memberId = authorization.getMember().getId();
            this.authorization = authorization.getAuthorization();
            this.memberType = authorization.getMemberType();
        }

        private Response(Member member) {
            this.memberId = member.getId();
            this.authorization = MemberType.ADMIN.getDescription();
            this.memberType = MemberType.ADMIN;
        }

        public static Response toDto(ProjectAuthorization authorization) {
            return new Response(authorization);
        }

        public static Response toAdminDto(Member member) {
            return new Response(member);
        }
    }
}
