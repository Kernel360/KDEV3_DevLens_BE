package com.seveneleven.service;

import com.seveneleven.common.CheckCompanyValidity;
import com.seveneleven.dto.*;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.YN;
import com.seveneleven.exception.CompanyDuplicatedException;
import com.seveneleven.exception.CompanyNotFoundException;
import com.seveneleven.repository.*;
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
public class AdminCompanyServiceImpl implements AdminCompanyService{
    private final CompanyRepository companyRepository;
    private final PutCompanyResponseConverter putCompanyResponseConverter;
    private final GetCompaniesResponseConverter getCompaniesResponseConverter;
    private final PutCompanyRequestConverter putCompanyRequestConverter;
    private final CheckCompanyValidity checkCompanyValidity;
    private final GetAllCompaniesConverter getAllCompaniesConverter;
    private final AdminCompanyStore adminCompanyStore;
    private final AdminCompanyReader adminCompanyReader;

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

    /*
        함수명 : getCompanyDto
        함수 목적 : 회사 상세조회
     */
    @Transactional(readOnly = true)
    @Override
    public GetCompanyDetail.Response getCompanyDetail(Long id) {
        //참여 프로젝트 조회를 위한 회사 조회
        Company company = adminCompanyReader.getActiveCompany(id);
        return GetCompanyDetail.Response.of(company);
    }

    /*
        함수명 : getListOfCompanies
        함수 목적 : 회사 목록조회
    */
    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<GetCompanies.Response> getListOfCompanies(Integer page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE.getPageSize(), Sort.by("companyName").ascending());
        Page<Company> companyPage = companyRepository.findByIsActive(pageable, YN.Y);
        if (companyPage.getContent().isEmpty()) {
            throw new CompanyNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(companyPage.map(getCompaniesResponseConverter::toDTO));
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
        Page<Company> companyPage = companyRepository.findByIsActiveAndCompanyNameContainingIgnoreCase(YN.Y, name, pageable);
        if (companyPage.getContent().isEmpty()) {
            throw new CompanyNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(companyPage.map(getCompaniesResponseConverter::toDTO));
    }

    @Transactional
    @Override
    public PutCompany.Response updateCompany(
            Long id, PutCompany.Request request
    ) {
        //비활성화 및 존재 여부 확인
        Company oldCompany = checkCompanyValidity.checkCompanyExistsOrDeactivated(id);
        //회사 isActive N으로 변경
        oldCompany.deleteCompany();
        //중복 회사 등록 번호 확인
        checkCompanyValidity.checkDuplicatedCompanyBusinessRegistrationNumber(request.getBusinessRegistrationNumber());
        //신규 데이터로 회사 생성
        Company company = putCompanyRequestConverter.toEntity(request);
        return putCompanyResponseConverter.toDTO(companyRepository.save(company));
    }

    @Transactional
    @Override
    public void deleteCompany(Long id) {
        //비활성화 및 존재 여부 확인
        Company company = checkCompanyValidity.checkCompanyExistsOrDeactivated(id);
        //회사 isActive N으로 변경
        company.deleteCompany();
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetAllCompanies> getAllCompanies() {
        return companyRepository.findAllByIsActiveOrderByCompanyNameAsc(YN.Y)
                .stream()
                .map(getAllCompaniesConverter::toDTO)
                .toList();
    }

    /*
        함수명 : checkDuplicatedCompany
        함수 목적 : 중복 회사 조회
     */
    public void checkDuplicatedCompanyBusinessRegistrationNumber(
            String businessRegistrationNumber
    ) {
        companyRepository.findByBusinessRegistrationNumberAndIsActive(businessRegistrationNumber, YN.Y)
                .ifPresent(company -> {
                    throw new CompanyDuplicatedException();
                });
    }

    /*
        함수명 : checkCompanyExistsOrDeactivated
        함수 목적 : 회사 존재여부 확인 및 비활성화 여부 확인
     */
    public Company checkCompanyExistsOrDeactivated(
            Long id
    ) {
        return companyRepository.findByIdAndIsActive(id, YN.Y)
                .orElseThrow(CompanyNotFoundException::new);
    }
}