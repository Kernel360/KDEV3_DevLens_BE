package com.seveneleven.project.repository;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.GetProjectDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
        SELECT p
        FROM Project p
        JOIN ProjectAuthorization p_a
            ON
                p_a.member.id = :memberId AND
                p_a.isDeleted = 'N' AND
                p_a.projectStep.project.id = p.id
        WHERE
            p.projectStatusCode = 'IN_PROGRESS'
    """)
    List<Project> findAllProgressingProjects(Long memberId);

    @Query("""
        SELECT p
        FROM Project p
        WHERE
            p.projectStatusCode = 'IN_PROGRESS' AND
            (
                p.customer.id = :companyId OR
                p.developer.id = :companyId
            )
    """)
    List<Project> findAllCompanyProgressingProjects(Long companyId);

    @Query(value = """
        SELECT
        new com.seveneleven.project.dto.GetProjectDetail$ProjectDetail(
                p.id,
                p_t.projectTypeName,
                p.projectName,
                p.projectDescription,
                m.name,
                m.phoneNumber
        )
        FROM
            Project p
        JOIN
            ProjectType p_t ON p_t.id = p.projectType.id
        JOIN
            Member m ON m.id = p.bnsManager.id
        WHERE
             p.id = :projectId
        """)
    GetProjectDetail.ProjectDetail getProjectDetail(@Param("projectId") Long projectId);
}
