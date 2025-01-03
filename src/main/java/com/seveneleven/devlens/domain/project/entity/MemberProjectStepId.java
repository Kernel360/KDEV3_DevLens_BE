package com.seveneleven.devlens.domain.project.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class MemberProjectStepId implements Serializable {

    private Long memberId; // 회원 ID
    private Long projectStepId; // 프로젝트 단계 ID

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberProjectStepId that = (MemberProjectStepId) o;
        return Objects.equals(memberId, that.memberId) && Objects.equals(projectStepId, that.projectStepId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, projectStepId);
    }
}
