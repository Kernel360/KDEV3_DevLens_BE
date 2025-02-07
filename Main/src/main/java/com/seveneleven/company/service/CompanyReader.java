package com.seveneleven.company.service;

import com.seveneleven.entity.member.Company;

public interface CompanyReader {
    Company read(Long companyId);
}
