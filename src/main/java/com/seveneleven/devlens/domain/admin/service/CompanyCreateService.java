package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.db.CompanyRequestConverter;
import com.seveneleven.devlens.domain.admin.db.CompanyResponseConverter;
import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.domain.admin.exception.CompanyDuplicatedException;
import com.seveneleven.devlens.domain.admin.db.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyCreateService {
    private final CompanyRepository companyRepository;
    private final CompanyRequestConverter companyRequestConverter;
    private final CompanyResponseConverter companyResponseConverter;

    /*
        함수명 : createCompany
        함수 목적 : 함수 정보 저장
     */
    public CompanyDto.CompanyResponse createCompany(CompanyDto.CompanyRequest companyRequest) {
            //사업자 등록번호 중복 조회
            checkDuplicatedCompany(companyRequest.getBusinessRegistrationNumber());
            //회사 저장, 대표 이미지 존재 여부는 파일 등록 시 변경
            var company = companyRequestConverter.toEntity(companyRequest);
            return companyResponseConverter.toDTO(companyRepository.save(company));
    }

    /*
        함수명 : checkDuplicatedCompany
        함수 목적 : 회사 중복 조회
     */
    private void checkDuplicatedCompany(String businessRegistrationNumber) {
        companyRepository.findByBusinessRegistrationNumber(businessRegistrationNumber)
                .ifPresent(company -> {
                    throw new CompanyDuplicatedException();
                });
    }
}
