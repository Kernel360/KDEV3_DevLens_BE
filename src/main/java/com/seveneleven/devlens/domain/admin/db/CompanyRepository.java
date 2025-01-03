package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.member.entity.Company;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByBusinessRegistrationNumber(String businessRegistrationNumber);
    Page<Company> findAll(Pageable pageable);
}