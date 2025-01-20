package com.seveneleven.member.dto;

import com.seveneleven.entity.member.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String loginId;
    private String name;
    private String email;
    private Role role;
    private String profileUrl;
    private Long companyId;
    private String companyName;
    private String department;
    private String position;
}
