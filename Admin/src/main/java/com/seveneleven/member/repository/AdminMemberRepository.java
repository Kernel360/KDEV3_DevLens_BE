package com.seveneleven.member.repository;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AdminMemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    Boolean existsByLoginId(String loginId);

    Boolean existsByEmail(String email);

    Optional<Member> findByLoginId(String loginId);

    Page<Member> findAll(Pageable pageable);

    List<Member> findAllByCompany(Company company);

    Optional<Member> findByLoginIdAndStatus(String loginId, MemberStatus statusCode);

    List<Member> findMembersByCompanyIdAndStatus(Long companyId, MemberStatus statusCode);

    Optional<Member> findByIdAndStatus(Long id, MemberStatus statusCode);
}
