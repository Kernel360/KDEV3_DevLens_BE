package com.seveneleven.devlens.domain.member.entity;

import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "department")
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false, referencedColumnName = "id")
    private Company company;

    @Column(name = "department_name", nullable = false, length = 50)
    private String departmentName;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false)
    @Enumerated(EnumType.STRING)
    private YN isActive = YN.Y;
}
