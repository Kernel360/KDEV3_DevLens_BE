package com.seveneleven.company.service;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.YN;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.CompanyRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CompanyReaderImpl implements CompanyReader {

    private final CompanyRepository companyRepository;

    @Override
    public Company read(Long companyId) {
        return companyRepository.findByIdAndIsActive(companyId, YN.Y)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));
    }
}
