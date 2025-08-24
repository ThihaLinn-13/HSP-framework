package com.hover.project.department.entity;

import com.hover.project.employee.entity.Employee;
import com.hover.project.position.entity.Position;
import com.hover.project.util.type.AuditableEntity;
import com.hover.project.util.type.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Department extends AuditableEntity {

    @NonNull
    private String name;

    @Column(unique = true)
    @NonNull
    private String code;

    @NonNull
    private String description;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();;

    @ManyToMany
    @JoinTable(name = "Department_Position", joinColumns = @JoinColumn(name = "department_id"), inverseJoinColumns = @JoinColumn(name = "position_id"))
    private List<Position> positions = new ArrayList<>();

    public Department(String name, String code, String description) {
        this.setName(name);
        this.setCode(code);
        this.setDescription(description);
    }

    public void addEmployee(Employee employee) {
        employee.setDepartment(this);
        employees.add(employee);
    }

}
