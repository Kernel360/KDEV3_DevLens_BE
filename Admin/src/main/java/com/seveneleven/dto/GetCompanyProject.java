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
    private String developerCompanyName;
    private String customerCompanyName;
    private String projectStatus;

    private GetCompanyProject(Project project) {
        id = project.getId();
        projectName = project.getProjectName();
        plannedStartDate = project.getPlannedStartDate();
        developerCompanyName = project.getDeveloper().getCompanyName();
        customerCompanyName = project.getCustomer().getCompanyName();
        projectStatus = project.getProjectStatusCode().name();
    }
    public static GetCompanyProject of(Project project) {
        return new GetCompanyProject(project);
    }

}
