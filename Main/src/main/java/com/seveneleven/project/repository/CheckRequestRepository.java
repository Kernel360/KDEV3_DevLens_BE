package com.seveneleven.project.repository;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.project.dto.GetProjectDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckRequestRepository extends JpaRepository<CheckRequest, Long> {

    @Query("""
    SELECT
        new com.seveneleven.project.dto.GetProjectDetail$ChecklistApplicationList(
        c_r.id,
        p_s.stepName,
        c.title,
        c_r.title,
        c_r.requester.name,
        c_r.requestDate
        )
    FROM CheckRequest c_r
    JOIN c_r.checklist c
    JOIN c.projectStep p_s
    WHERE p_s.project.id = :projectId
    """)
    List<GetProjectDetail.ChecklistApplicationList> findAllApplicationLists(Long projectId);

    Optional<CheckRequest> findByIdAndIsActive(Long id, YesNo isActive);
}
