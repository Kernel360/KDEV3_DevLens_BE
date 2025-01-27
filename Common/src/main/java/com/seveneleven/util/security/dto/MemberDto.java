package com.seveneleven.util.security.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.Role;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

/*
 * security, jwt 테스트 확인을 위해 임시로 만든 dto
 *
 * */
@Getter
@Setter
@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String loginId;
    private String username;
    private String email;
    private String password;
    private Role role;

    // **Member로 변환하는 메서드**
    private static final ModelMapper modelMapper = new ModelMapper();

    public static MemberDto fromEntity(Member member) {
        log.info("member id : {}", member.getId());
        return modelMapper.map(member, MemberDto.class);
    }

}
