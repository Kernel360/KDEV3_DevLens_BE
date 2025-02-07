package com.seveneleven.company.service;

import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyReader companyReader;

    @Override
    public Company getCompany(Long companyId) {
        return companyReader.read(companyId);
    }
}
