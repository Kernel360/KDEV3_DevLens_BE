package com.seveneleven.member.service;

import com.seveneleven.entity.member.Member;

import java.util.List;

public interface AdminMemberReader {
    Member getMember(Long id);
    List<Member> getCompanyMember(Long id);
}

