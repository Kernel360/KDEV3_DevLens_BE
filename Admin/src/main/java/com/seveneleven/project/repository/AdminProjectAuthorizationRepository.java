package com.seveneleven.project.repository;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminProjectAuthorizationRepository extends JpaRepository<ProjectAuthorization, Long> {

    List<ProjectAuthorization> findByProjectIdAndIsActive(Long projectId, YesNo isActive);
}
