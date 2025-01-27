package com.seveneleven.service;

import com.seveneleven.dto.GetProject;
import com.seveneleven.dto.PostProject;
import com.seveneleven.dto.PutProject;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectType;
import com.seveneleven.exception.ProjectNameDuplicatedException;
import com.seveneleven.exception.ProjectNotFoundException;
import com.seveneleven.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.seveneleven.common.PageSize.DEFAULT_PAGE_SIZE;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminProjectServiceImpl implements AdminProjectService {
    private final AdminProjectReader adminProjectReader;
    private final AdminProjectStore adminProjectStore;
    private final AdminCompanyReader adminCompanyReader;
    private final AdminProjectTypeReader adminProjectTypeReader;

    @Transactional
    @Override
    public Project createProject(PostProject.Request request) {
        //프로젝트 이름 중복 체크
        if(adminProjectReader.checkProjectExists(request.getProjectName())){
            throw new ProjectNameDuplicatedException();
        }

        Company customer = adminCompanyReader.getCompany(request.getCustomerId());
        Company developer = adminCompanyReader.getCompany(request.getDeveloperId());
        ProjectType projectType = adminProjectTypeReader.getProjectType(request.getProjectTypeId());

        return adminProjectStore.store(request.toEntity(customer, developer, projectType));
    }

    @Transactional(readOnly = true)
    @Override
    public GetProject.Response getProject(Long id) {
        return GetProject.Response.of(adminProjectReader.getProject(id));
    }

    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<GetProject.Response> getListOfProject(Integer page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE.getPageSize(), Sort.by("id").descending());
        Page<Project> projects = adminProjectReader.getProjectList(pageable);
        if (projects.getContent().isEmpty()) throw new ProjectNotFoundException();

        return PaginatedResponse.createPaginatedResponse(projects.map(GetProject.Response::of));
    }

    @Transactional
    @Override
    public Project updateProject(Long id, PutProject.Request request) {
        Project project = adminProjectReader.getProject(id);
        //현재 프로젝트 명과 다르면 중복 체크
        if (!project.getProjectName().equals(request.getProjectName())) {
            if(adminProjectReader.checkProjectExists(request.getProjectName())){
                throw new ProjectNameDuplicatedException();
            }
        }

        Company customer = adminCompanyReader.getCompany(request.getCustomerId());
        Company developer = adminCompanyReader.getCompany(request.getDeveloperId());
        ProjectType projectType = adminProjectTypeReader.getProjectType(request.getProjectTypeId());

        Project updatedProject = request.updateProject(project, customer, developer, projectType);
        return adminProjectStore.store(updatedProject);
    }

    @Transactional
    @Override
    public Project deleteProject(Long id) {
        Project project = adminProjectReader.getProject(id);
        return adminProjectStore.delete(project);
    }

    @Transactional
    @Override
    public String checkProjectNameExists(String projectName) {
        if(adminProjectReader.checkProjectExists(projectName)){
            return "이미 존재하는 프로젝트 이름입니다.";
        }
        else
            return "생성 가능한 프로젝트 이름입니다.";
    }

    /*
        함수명 : getCompanyProject
        함수 목적 : 회사 참여 프로젝트
     */
    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<GetProject.Response> getCompanyProject(Integer pageNumber, Long id) {
        Pageable pageable = PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE.getPageSize());
        Page<GetProject.Response> page = adminCompanyReader.getCompanyProject(pageable, id);
        return PaginatedResponse.createPaginatedResponse(page);
    }
}