package com.seveneleven.devlens.domain.member.entity;

import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Position> positions = new ArrayList<>(); // 부서에 속한 직책 리스트

    @Column(name = "department_name", nullable = false, length = 50)
    private String departmentName;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false)
    @Enumerated(EnumType.STRING)
    private YN isActive = YN.Y;

    // 생성 메서드
    public static Department createDepartment(Company company, String departmentName, String description) {
        Department department = new Department();
        department.company        = company;
        department.description    = description;
        department.departmentName = departmentName;
        return department;
    }

    // 회사 설정 메서드
    public void setCompany(Company company) {
        this.company = company;
    }

    // 직책 추가 메서드
    public void addPosition(Position position) {
        this.positions.add(position);
        position.setDepartment(this);
    }

    // 직책 삭제 메서드
    public void removePosition(Position position) {
        this.positions.remove(position);
        position.setDepartment(null);
    }

    // 삭제 메서드
    public void deleteDepartment() {
        this.isActive = YN.N;
    }
}
