package com.hover.project.employee.entity;

import com.hover.project.department.entity.Department;
import com.hover.project.position.entity.Position;
import com.hover.project.role.entity.Role;
import com.hover.project.util.type.AuditableEntity;
import com.hover.project.util.type.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Employee extends AuditableEntity {

    private String employeeId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dateOfBirth;

    private String nrcNumber;

    private LocalDate dateOfEmployment;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "employee_role", // join table
            joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public void addPosition(Position position) {
        position.setEmployee(this);
        this.setPosition(position);
    }

    public void addDepartment(Department department) {
        department.addEmployee(this);
    }

}
