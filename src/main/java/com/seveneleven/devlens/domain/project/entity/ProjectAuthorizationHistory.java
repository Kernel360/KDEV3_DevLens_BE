package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "project_authorization_history")
public class ProjectAuthorizationHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 프로젝트 권한 목록 이력 ID

    @Column(name = "member_name", nullable = false, length = 255)
    private String memberName; // 회원 이름

    @Column(name = "project_step_name", nullable = false, length = 255)
    private String projectStepName; // 프로젝트 단계 이름

    @Column(name = "member_type", nullable = false, length = 50)
    private String memberType; // 회원 구분 (client, developer)

    @Column(name = "authorization_code", nullable = false, length = 50)
    private String authorizationCode; // 권한 코드

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted; // 삭제 여부

    @CreatedDate
    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate; // 등록일시

    @LastModifiedDate
    @Column(name = "modification_date")
    private LocalDateTime modificationDate; // 수정일시

    @CreatedBy
    @Column(name = "registered_by", length = 100)
    private String registeredBy; // 등록자

    @LastModifiedBy
    @Column(name = "modified_by", length = 100)
    private String modifiedBy; // 수정자
}