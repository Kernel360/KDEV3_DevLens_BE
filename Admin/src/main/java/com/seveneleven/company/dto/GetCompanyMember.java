package com.seveneleven.company.dto;

import com.seveneleven.entity.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class GetCompanyMember {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long companyId;
        private List<CompanyMember> companyMemberList;

        private Response(Long companyId, List<CompanyMember> companyMemberList) {
            this.companyId = companyId;
            this.companyMemberList = companyMemberList;
        }

        public static Response toDto(Long companyId, List<Member> companyMemberList) {
            return new Response(companyId, CompanyMember.toDto(companyMemberList));
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CompanyMember {
        private Long memberId;
        private String memberName;
        private String department;
        private String position;

        public CompanyMember(Member member) {
            this.memberId = member.getId();
            this.memberName = member.getName();
            this.department = member.getDepartment();
            this.position = member.getPosition();
        }

        public static List<CompanyMember> toDto(List<Member> members) {
            return members.stream()
                    .map(CompanyMember::new)
                    .toList();
        }
    }
}
