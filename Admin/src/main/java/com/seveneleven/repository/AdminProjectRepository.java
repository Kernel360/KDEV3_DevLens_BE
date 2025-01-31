package com.seveneleven.repository;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.constant.ProjectStatusCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectNameAndProjectStatusCodeNot(String projectName, ProjectStatusCode projectStatusCode);
    Page<Project> findByCustomerOrDeveloper(Pageable pageable, Company customer, Company developer);

    Optional<Project> findByIdAndProjectStatusCodeNot(Long id, ProjectStatusCode projectStatusCode);
    Page<Project> findAllByProjectStatusCodeNot(Pageable pageable, ProjectStatusCode projectStatusCode);
}