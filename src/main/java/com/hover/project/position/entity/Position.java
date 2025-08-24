package com.hover.project.position.entity;

import com.hover.project.department.entity.Department;
import com.hover.project.employee.entity.Employee;
import com.hover.project.util.type.AuditableEntity;
import com.hover.project.util.type.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position  extends AuditableEntity {


    private String name;

    private String code;

    private String description;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Employee employee;

    @ManyToMany(mappedBy = "positions")
    private List<Department> Department;

    public Position(String name,String code,String description){
        this.name = name;
        this.code = code;
        this.description = description;
    }


}
