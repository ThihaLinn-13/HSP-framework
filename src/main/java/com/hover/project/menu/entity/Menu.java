package com.hover.project.menu.entity;

import com.hover.project.role.entity.Role;
import com.hover.project.util.type.AuditableEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class Menu extends AuditableEntity {

    private String name;

    private String path;

    @ManyToMany(mappedBy = "menus")
    private List<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToMany(mappedBy = "menu",cascade = CascadeType.ALL)
    private Set<Menu> subMenus = new LinkedHashSet<>();

}
