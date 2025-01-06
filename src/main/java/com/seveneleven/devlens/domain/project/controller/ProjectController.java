package com.seveneleven.devlens.domain.project.controller;

import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.project.dto.GetProjectDetail;
import com.seveneleven.devlens.domain.project.dto.GetProjectStep;
import com.seveneleven.devlens.global.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/detail")
public class ProjectController implements ProjectDocs {

    /**
     * 함수명 : getProjectDetail
     * 프로젝트 상세 페이지를 반환하는 함수
     */
    @GetMapping("/{projectId}")
    public APIResponse<GetProjectDetail.Response> getProjectDetail(
            @PathVariable Long projectId
    ) {
        GetProjectDetail.ProjectStepInfo projectStep = new GetProjectDetail.ProjectStepInfo(
                10L,               // stepId
                "Design Phase",    // stepName
                75.5               // stepProcessRate
        );

        List<GetProjectDetail.ChecklistApplicationList> checklistApplications = List.of(
                new GetProjectDetail.ChecklistApplicationList(
                        1L,                                     // checklistApplicationId
                        "Step A",                               // StepName
                        "Checklist A",                          // checklistName
                        "Application A",                        // applicationTitle
                        "User A",                               // applicationUserName
                        LocalDateTime.of(2023, 1, 1, 10, 0)     // ApplicationDateTime
                ),
                new GetProjectDetail.ChecklistApplicationList(
                        2L,                                     // checklistApplicationId
                        "Step B",                               // StepName
                        "Checklist B",                          // checklistName
                        "Application B",                        // applicationTitle
                        "User B",                               // applicationUserName
                        LocalDateTime.of(2023, 2, 1, 15, 30)    // ApplicationDateTime
                )
        );

        GetProjectDetail.Response response = new GetProjectDetail.Response(
                projectId,                                 // projectId
                "Construction",                            // projectType
                "New Building Project",                   // projectName
                projectStep,                               // projectStep
                "This is a sample project description.",   // projectDescription
                "John Doe",                                // projectContact
                "+1-234-567-890",                          // projectContactPhone
                "https://example.com/image.jpg",           // projectImageURL
                new PageImpl<>(checklistApplications)      // checklistApplicationList
        );

        return APIResponse.success(response);
    }

    /**
     * 함수명 : getProjectStep
     * 해당 프로젝트의 단계들을 반환하는 함수
     */
    @GetMapping("/step/{projectId}")
    public APIResponse<GetProjectStep.Response> getProjectStep(
            @PathVariable Long projectId
    ) {
        List<GetProjectStep.ProjectChecklist> checklists1 = List.of(
                new GetProjectStep.ProjectChecklist(
                        101L,                      // checklistId
                        "Checklist Item 1",        // checklistName
                        YN.Y                       // checklistStatus
                ),
                new GetProjectStep.ProjectChecklist(
                        102L,                      // checklistId
                        "Checklist Item 2",        // checklistName
                        YN.N                       // checklistStatus
                )
        );

        List<GetProjectStep.ProjectChecklist> checklists2 = List.of(
                new GetProjectStep.ProjectChecklist(
                        201L,                      // checklistId
                        "Checklist Item A",        // checklistName
                        YN.Y                       // checklistStatus
                ),
                new GetProjectStep.ProjectChecklist(
                        202L,                      // checklistId
                        "Checklist Item B",        // checklistName
                        YN.N                       // checklistStatus
                )
        );

        List<GetProjectStep.ProjectStepInfo> steps = List.of(
                new GetProjectStep.ProjectStepInfo(
                        1L,                        // stepId
                        "Step 1",                  // stepName
                        checklists1                // projectChecklist
                ),
                new GetProjectStep.ProjectStepInfo(
                        2L,                        // stepId
                        "Step 2",                  // stepName
                        checklists2                // projectChecklist
                )
        );

        return APIResponse.success(new GetProjectStep.Response(projectId,steps));
    }
}