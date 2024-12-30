package com.seveneleven.devlens.domain.project.entity;

import java.io.Serializable;
import java.util.Objects;

public class ProjectAuthorizationId implements Serializable {
    private Long memberId;
    private Long projectStepId;

    public ProjectAuthorizationId() {}

    public ProjectAuthorizationId(Long memberId, Long projectStepId) {
        this.memberId = memberId;
        this.projectStepId = projectStepId;
    }

    // equals()와 hashCode() 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectAuthorizationId that = (ProjectAuthorizationId) o;
        return Objects.equals(memberId, that.memberId) &&
                Objects.equals(projectStepId, that.projectStepId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, projectStepId);
    }
}