package com.seveneleven.company.service;

import com.seveneleven.company.dto.*;
import com.seveneleven.company.exception.CompanyDuplicatedException;
import com.seveneleven.company.exception.CompanyNotFoundException;
import com.seveneleven.company.repository.CompanyRepository;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.YN;
import com.seveneleven.member.service.AdminMemberReader;
import com.seveneleven.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seveneleven.common.PageSize.DEFAULT_PAGE_SIZE;

@Service
@RequiredArgsConstructor
public class AdminCompanyServiceImpl implements AdminCompanyService {
    private final CompanyRepository companyRepository;
    private final AdminCompanyStore adminCompanyStore;
    private final AdminCompanyReader adminCompanyReader;
    private final AdminMemberReader adminMemberReader;

    /*
        함수명 : createCompany
        함수 목적 : 함수 정보 저장
     */
    @Transactional
    @Override
    public PostCompany.Response createCompany(PostCompany.Request companyRequest) {
        //사업자 등록번호 중복 조회
        checkDuplicatedCompanyBusinessRegistrationNumber(companyRequest.getBusinessRegistrationNumber());

        return PostCompany.Response.of(adminCompanyStore.store(companyRequest.toEntity()));
    }

    @Override
    public Company getCompany(Long companyId) {
        return adminCompanyReader.getCompany(companyId);
    }

    /*
            함수명 : getCompanyDto
            함수 목적 : 회사 상세조회
         */
    @Transactional(readOnly = true)
    @Override
    public GetCompanyDetail.Response getCompanyDetail(Long id) {
        //참여 프로젝트 조회를 위한 회사 조회
        Company company = adminCompanyReader.getCompany(id);
        return GetCompanyDetail.Response.of(company);
    }

    /*
        함수명 : getListOfCompanies
        함수 목적 : 회사 목록조회
    */
    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<GetCompanies.Response> getListOfCompanies(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE.getPageSize(), Sort.by("companyName").ascending());
        Page<GetCompanies.Response> companyPage = adminCompanyReader.getCompanies(pageable);
        if (companyPage.getContent().isEmpty()) {
            throw new CompanyNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(companyPage);
    }

    /*
            함수명 : searchCompaniesByName
            함수 목적 : 회사 검색
     */
    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<GetCompanies.Response> searchCompaniesByName(
            String name, Integer page
    ) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE.getPageSize(), Sort.by("companyName").ascending());
        Page<GetCompanies.Response> companyPage = adminCompanyReader.getCompaniesBySearchTerm(name, pageable);
        if (companyPage.getContent().isEmpty()) {
            throw new CompanyNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(companyPage);
    }

    /*
            함수명 : updateCompany
            함수 목적 : 회사 수정
     */
    @Transactional
    @Override
    public PutCompany.Response updateCompany(
            Long id, PutCompany.Request request
    ) {
        //비활성화 및 존재 여부 확인
        Company company = adminCompanyReader.getActiveCompany(id);

        //중복 회사 등록 번호 확인
        if(!request.getBusinessRegistrationNumber().equals(company.getBusinessRegistrationNumber())) {
            checkDuplicatedCompanyBusinessRegistrationNumber(request.getBusinessRegistrationNumber());
        }
        //신규 데이터로 회사 생성
        Company newCompany = request.updateCompany(company);

        return PutCompany.Response.of(adminCompanyStore.store(newCompany));
    }

    /*
            함수명 : deleteCompany
            함수 목적 : 회사 삭제
     */
    @Transactional
    @Override
    public Company changeCompanyIsActive(Long id) {
        //회사 조회
        Company company = adminCompanyReader.getCompany(id);
        //회사 isActive N으로 변경
        return adminCompanyStore.store(company.changeCompanyIsActive());
    }

    /*
            함수명 : getAllCompanies
            함수 목적 : 회사 id 이름 프론트 전달
     */
    @Transactional(readOnly = true)
    @Override
    public List<GetAllCompanies> getAllCompanies() {
        return adminCompanyReader.getAllCompanies();
    }

    /*
        함수명 : checkDuplicatedCompany
        함수 목적 : 중복 회사 조회
     */
    @Transactional(readOnly = true)
    protected void checkDuplicatedCompanyBusinessRegistrationNumber(
            String businessRegistrationNumber
    ) {
        companyRepository.findByBusinessRegistrationNumberAndIsActive(businessRegistrationNumber, YN.Y)
                .ifPresent(company -> {
                    throw new CompanyDuplicatedException();
                });
    }

    @Override
    public GetCompanyMember.Response getCompanyMember(Long companyId) {
        return GetCompanyMember.Response.toDto(
                companyId,
                adminMemberReader.getCompanyMember(companyId)
        );
    }
}