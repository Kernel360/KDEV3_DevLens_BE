package com.seveneleven.dto;

import com.seveneleven.entity.project.Project;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCompanyProject {
    private Long id;
    private String projectName;
    private LocalDate plannedStartDate;
    private String developerName;
    private String customerName;
    private String projectStatus;

    private GetCompanyProject(Project project) {
        id = project.getId();
        projectName = project.getProjectName();
        plannedStartDate = project.getPlannedStartDate();
        developerName = project.getDeveloper().getCompanyName();
        customerName = project.getCustomer().getCompanyName();
        projectStatus = project.getProjectStatusCode().name();
    }
    public static GetCompanyProject of(Project project) {
        return new GetCompanyProject(project);
    }

}
