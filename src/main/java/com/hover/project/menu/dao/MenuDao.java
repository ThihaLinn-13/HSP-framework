package com.hover.project.menu.dao;

import com.hover.project.menu.entity.Menu;
import com.hover.project.role.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuDao extends JpaRepository<Menu, UUID> {

    Optional<Menu> findByIdAndRecordStatus(UUID id, int recordStatus);

    Page<Menu> findAllByRecordStatus(int recordStatus, Pageable pageable);

}
