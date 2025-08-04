package com.hover.project.menu.dao;

import com.hover.project.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MenuDao extends JpaRepository<Menu, UUID> {



}
