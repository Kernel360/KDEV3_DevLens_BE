package com.seveneleven.devlens.domain.admin.exception;

import com.seveneleven.devlens.global.exception.BusinessException;

import static com.seveneleven.devlens.global.response.ErrorCode.*;
public class CompanyDuplicatedException extends BusinessException {
  public CompanyDuplicatedException(String message) {
    super(message, COMPANY_DUPLICATED_NUMBER);
  }

  public CompanyDuplicatedException() {
    this(COMPANY_DUPLICATED_NUMBER.getMessage());
  }
}
