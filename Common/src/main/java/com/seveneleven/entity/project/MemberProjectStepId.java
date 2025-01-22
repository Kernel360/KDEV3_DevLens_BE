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
    private Long projectStepId; // 프로젝트 단계 ID

    public MemberProjectStepId(Long memberId, Long projectStepId) {
        if(memberId == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        if(projectStepId == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_PROJECT_STEP);
        }

        this.memberId = memberId;
        this.projectStepId = projectStepId;
    }

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
