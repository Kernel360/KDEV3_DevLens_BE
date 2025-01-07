package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.global.entity.BaseEntity;
import com.seveneleven.devlens.global.entity.YesNo;
import com.seveneleven.devlens.global.entity.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_authorization")
public class ProjectAuthorization extends BaseEntity {

    @EmbeddedId
    private MemberProjectStepId id; // 복합 키

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId") // 복합 키의 memberId와 매핑
    @JoinColumn(name = "member_id", nullable = false, referencedColumnName = "id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectStepId") // 복합 키의 projectStepId와 매핑
    @JoinColumn(name = "project_step_id", nullable = false)
    private ProjectStep projectStep;

    @Enumerated(EnumType.STRING)
    private memberType memberType; // 회원 구분 (client, developer)

    private String authorizationCode; // 권한 코드

    @Convert(converter = YesNoConverter.class)
    private YesNo isDeleted; // 삭제 여부

    @Getter
    @AllArgsConstructor
    enum memberType {
        CLIENT("client"),
        DEVELOPER("developer");

        final String description;
    }
}