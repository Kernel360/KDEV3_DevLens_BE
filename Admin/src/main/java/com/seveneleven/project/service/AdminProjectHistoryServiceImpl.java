package com.seveneleven.project.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectHistory;
import com.seveneleven.member.repository.AdminMemberRepository;
import com.seveneleven.project.dto.GetProjectHistory;
import com.seveneleven.project.exception.ProjectHistoryNotFoundException;
import com.seveneleven.project.repository.AdminProjectHistoryRepository;
import com.seveneleven.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.seveneleven.common.PageSize.DEFAULT_PAGE_SIZE;

@RequiredArgsConstructor
@Service
public class AdminProjectHistoryServiceImpl implements AdminProjectHistoryService{
    private final AdminProjectHistoryRepository projectHistoryRepository;
    private final AdminMemberRepository adminMemberRepository;
    private final AdminProjectHistoryStore adminProjectHistoryStore;
    private final AdminProjectHistoryReader adminProjectHistoryReader;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProjectHistory(Project project) {
        // 이력 저장
        adminProjectHistoryStore.store(project.saveHistory());
    }

    @Transactional(readOnly = true)
    @Override
    public GetProjectHistory.Response getProjectHistory(Long id) {
        ProjectHistory projectHistory = adminProjectHistoryReader.getProjectHistory(id);
        String createdBy = adminMemberRepository.findById(projectHistory.getCreatedBy()).map(Member::getName).orElse(null);
        String updatedBy = adminMemberRepository.findById(projectHistory.getUpdatedBy()).map(Member::getName).orElse(null);
        return GetProjectHistory.Response.of(projectHistory, createdBy, updatedBy);
    }

    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<GetProjectHistory.Response> getListOfProjectHistory(Integer page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE.getPageSize(), Sort.by("id").descending());
        Page<ProjectHistory> projectHistories = projectHistoryRepository.findAll(pageable);

        if (projectHistories.getContent().isEmpty()) {
            throw new ProjectHistoryNotFoundException();
        }

        return PaginatedResponse.createPaginatedResponse(
                projectHistories.map(projectHistory -> {
                    String createdBy = adminMemberRepository.findById(projectHistory.getCreatedBy()).map(Member::getName).orElse(null);
                    String updatedBy = adminMemberRepository.findById(projectHistory.getUpdatedBy()).map(Member::getName).orElse(null);
                    return GetProjectHistory.Response.of(projectHistory, createdBy, updatedBy);
                })
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<GetProjectHistory.Response> searchHistoryByProjectName(String searchTerm, Integer page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE.getPageSize(), Sort.by("projectName").ascending());
        Page<ProjectHistory> historyPage = projectHistoryRepository.findByProjectNameContainingIgnoreCase(searchTerm ,pageable);
        return PaginatedResponse.createPaginatedResponse(historyPage.map(GetProjectHistory.Response::from));
    }
}
