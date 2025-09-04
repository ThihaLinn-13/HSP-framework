package com.hover.project.menu.entity;

import com.hover.project.role.entity.Role;
import com.hover.project.util.type.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Menu extends AuditableEntity {

    private String name;

    private String path;

    @ManyToMany(mappedBy = "menus")
    private List<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToMany(mappedBy = "menu",cascade = CascadeType.PERSIST)
    private Set<Menu> subMenus = new LinkedHashSet<>();

}
