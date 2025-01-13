package com.seveneleven.project.service.dashboard;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.GetProjectList;

public interface ProjectReader {
    Project read(Long projectId);
    GetProjectList.Response getProjectList(Long memberId, Long companyId);
    GetProjectDetail.Response getProjectDetail(Long projectId);
}