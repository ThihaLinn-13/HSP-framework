package com.hover.project.role.entity;

import com.hover.project.employee.entity.Employee;
import com.hover.project.menu.entity.Menu;
import com.hover.project.util.type.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Role extends AuditableEntity {

    @NonNull
    private String name;

    @NonNull
    private String code;

    @NonNull
    private String description;

    @ManyToMany(mappedBy = "roles",cascade = CascadeType.PERSIST)
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "role_menu", // join table
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<Menu> menus = new ArrayList<>();



}
