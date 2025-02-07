package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.global.converter.YesNoConverter;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.constant.MemberType;
import com.seveneleven.entity.project.duplkey.MemberProjectStepId;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    @MapsId("projectId") // 복합 키의 projectStepId와 매핑
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    private MemberType memberType; // 회원 구분 (client, developer)

    // Question - Enum으로 뺄까?
    private String authorizationCode; // 권한 코드

    @Convert(converter = YesNoConverter.class)
    private YesNo isActive; // 삭제 여부

    private ProjectAuthorization(Member member, Project project, MemberType memberType, String authorizationCode) {
        this.id = new MemberProjectStepId(member.getId(), project.getId());
        this.member = member;
        this.project = project;
        this.memberType = memberType;
        this.authorizationCode = authorizationCode;
        this.isActive = YesNo.YES;
    }

    public static ProjectAuthorization create(Member member, Project project, MemberType memberType, String authorizationCode) {
        return new ProjectAuthorization(member, project, memberType, authorizationCode);
    }

    public void edit(MemberType memberType, String authorizationCode) {
        this.memberType = memberType;
        this.authorizationCode = authorizationCode;
    }

    public ProjectAuthorizationHistory delete() {
        isActive = YesNo.NO;
        return ProjectAuthorizationHistory.create(this);
    }
}