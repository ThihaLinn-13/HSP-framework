package com.hover.project.position.entity;

import com.hover.project.department.entity.Department;
import com.hover.project.employee.entity.Employee;
import com.hover.project.util.type.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position extends AuditableEntity {

    private String name;

    private String code;

    private String description;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Employee employee;

    @ManyToMany(mappedBy = "positions")
    private List<Department> Department;

    public Position(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

}
