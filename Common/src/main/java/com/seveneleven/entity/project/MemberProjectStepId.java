package com.seveneleven.entity.project;

import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class MemberProjectStepId implements Serializable {

    private Long memberId; // 회원 ID
    private Long projectId; // 프로젝트 단계 ID

    public MemberProjectStepId(Long memberId, Long projectId) {
        if(memberId == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        if(projectId == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_PROJECT_STEP);
        }

        this.memberId = memberId;
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberProjectStepId that = (MemberProjectStepId) o;
        return Objects.equals(memberId, that.memberId) && Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, projectId);
    }
}
