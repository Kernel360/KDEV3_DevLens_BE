package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.global.YesNo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_tag")
public class ProjectTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private String tag;

    @Enumerated(EnumType.STRING)
    private YesNo IsActive;

    public ProjectTag(Project project, String tag) {
        this.project = project;
        this.tag = tag;
        IsActive = YesNo.YES;
    }

    public static ProjectTag create(Project project, String tag) {
        return new ProjectTag(project, tag);
    }
}
