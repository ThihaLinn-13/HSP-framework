package com.hover.project.employee.dao;

import com.hover.project.employee.entity.Employee;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByUserNameOrEmail(String userName,String email);

    Optional<Employee> findByUserName(String userName);
}
