package com.seveneleven.project.repository;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.project.dto.GetStepChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    @Query("""
        SELECT
            new com.seveneleven.project.dto.GetStepChecklist$ProjectChecklist(
                    c.id,
                    c.title,
                    c.isChecked,
                    c.approvalDate
                )
        FROM Checklist c
        WHERE c.projectStep.id = :projectStepId AND c.isActive = 'Y'
        ORDER BY c.id
    """)
    List<GetStepChecklist.ProjectChecklist> findStepChecklist(@Param("stepId") Long stepId, @Param("isActive") YesNo isActive);

    Optional<Checklist> findByIdAndIsActive(@Param("id") Long checklistId, @Param("isActive") YesNo isActive);

    List<Checklist> findByProjectStepIdAndIsActive(@Param("projectStepId") Long projectStepId, YesNo isActive);
}