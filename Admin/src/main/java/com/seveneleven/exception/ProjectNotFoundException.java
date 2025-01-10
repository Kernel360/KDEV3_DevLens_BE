package com.seveneleven.exception;

import static com.seveneleven.response.ErrorCode.PROJECT_NOT_FOUND;

public class ProjectNotFoundException extends BusinessException {
    public ProjectNotFoundException(String message) {
        super(message, PROJECT_NOT_FOUND);
    }

    public ProjectNotFoundException() {
        this(PROJECT_NOT_FOUND.getMessage());
    }
}
