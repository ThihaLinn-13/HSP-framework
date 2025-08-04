package com.hover.project.department.dao;

import com.hover.project.department.entity.Department;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends JpaRepository<Department, UUID> {
}
