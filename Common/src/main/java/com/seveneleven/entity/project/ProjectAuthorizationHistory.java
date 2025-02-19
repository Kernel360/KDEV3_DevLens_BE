package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.global.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_authorization_history")
public class ProjectAuthorizationHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false)
    private String memberType; // 회원 구분 (client, developer)

    @Column(nullable = false)
    private String authorizationCode;

    @Column(nullable = false)
    @Convert(converter = YesNoConverter.class)
    private YesNo isActive;

    private ProjectAuthorizationHistory(ProjectAuthorization authorization) {
        this.memberName = authorization.getMember().getName();
        this.projectName = authorization.getProject().getProjectName();
        this.memberType = authorization.getMemberType().name();
        this.authorizationCode = authorization.getAuthorization();
        this.isActive = authorization.getIsActive();
    }

    public static ProjectAuthorizationHistory create(ProjectAuthorization authorization) {
        return new ProjectAuthorizationHistory(authorization);
    }
}