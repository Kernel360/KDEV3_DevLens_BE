package com.seveneleven.project.dto;

import com.seveneleven.entity.project.constant.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GetMemberAuthorization {

    @Getter
    @NoArgsConstructor
    public static class Response {
        Long memberId;
        String authorization;
        MemberType memberType;

        private Response(Long memberId, String authorization, MemberType memberType) {
            this.memberId = memberId;
            this.authorization = authorization;
            this.memberType = memberType;
        }

        public static Response toDto() {
            // TODO - 구현
            return null;
        }
    }
}
