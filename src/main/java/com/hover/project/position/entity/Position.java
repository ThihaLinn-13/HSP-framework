package com.hover.project.position.entity;

import com.hover.project.department.entity.Department;
import com.hover.project.employee.entity.Employee;
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
public class Position {

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

    @OneToOne(cascade = CascadeType.PERSIST)
    private Employee employee;

    @ManyToMany(mappedBy = "positions")
    private List<Department> Department;



}
