package com.hover.project.department.entity;

import com.hover.project.employee.entity.Employee;
import com.hover.project.position.entity.Position;
import com.hover.project.util.type.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Department {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( updatable = false, nullable = false)
    private UUID id;

    @Column( unique = true, updatable = false, insertable = false)
    private Long autokey;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updatedAt;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    private long recordStatus;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();;

    @ManyToMany
    @JoinTable(name = "Department_Positon",joinColumns = @JoinColumn(name = "department_id"),
    inverseJoinColumns = @JoinColumn(name = "position_id"))
    private List<Position> positions = new ArrayList<>();;



}
