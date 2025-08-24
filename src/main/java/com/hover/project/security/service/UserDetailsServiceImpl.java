package com.hover.project.security.service;

import com.hover.project.employee.dao.EmployeeDao;
import com.hover.project.employee.entity.Employee;
import com.hover.project.security.model.JwtUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        Employee employee = employeeDao.findByUserNameOrEmailAndRecordStatus(userNameOrEmail, userNameOrEmail, 1)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with userNameOrEmail: " + userNameOrEmail));
        return new JwtUserPrincipal(
                employee.getId(),
                employee.getUserName(),
                employee.getPassword(),
                new ArrayList<>() // or employee roles
        );
    }
}
