package com.seveneleven.member.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.member.dto.LoginPost;
import com.seveneleven.member.dto.MemberPatch;
import com.seveneleven.util.security.CustomUserDetails;

public interface MemberService {

    LoginPost.Response login(LoginPost.Request request);
    void logout(String token);
    MemberPatch.Response resetPassword(CustomUserDetails userDetails, MemberPatch.Request request);

    Long getCompanyIdById(Long memberId);
    Member getMember(Long memberId);
}
