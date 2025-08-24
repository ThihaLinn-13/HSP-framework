package com.hover.project.auth.impl;

import com.hover.project.auth.dto.AuthRequestDto;
import com.hover.project.auth.dto.TokenResponseDto;
import com.hover.project.auth.service.AuthService;
import com.hover.project.department.entity.Department;
import com.hover.project.employee.dao.EmployeeDao;
import com.hover.project.employee.entity.Employee;
import com.hover.project.exception.handler.AuthenticationException;
import com.hover.project.position.entity.Position;
import com.hover.project.role.entity.Role;
import com.hover.project.security.service.JwtService;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.Gender;
import com.hover.project.util.type.HttpStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public TokenResponseDto login(AuthRequestDto authRequestDto) {
        Employee emp = employeeDao
                .findByUserNameOrEmailAndRecordStatus(authRequestDto.userName(), authRequestDto.password(), 1)
                .orElseThrow(() -> new AuthenticationException("Invalid username or email") {
                });

        if (!passwordEncoder.matches(authRequestDto.password(), emp.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        String accessToken = jwtService.generateAccessToken(emp);
        String refreshToken = jwtService.generateRefreshToken(emp);

        return new TokenResponseDto(accessToken, refreshToken);
    }

    @Override
    public ApiResponse<TokenResponseDto> refreshToken(TokenResponseDto tokenResponseDto) {

        if (!jwtService.isAccessToken(tokenResponseDto.accessToken())) {
            throw new IllegalArgumentException("Invalid access token type");
        }

        if (!jwtService.isRefreshToken(tokenResponseDto.refreshToken())) {
            throw new IllegalArgumentException("Invalid refresh token type");
        }

        if (!jwtService.validateToken(tokenResponseDto.accessToken())) {
            throw new IllegalArgumentException("Access token is invalid or expired");
        }

        if (!jwtService.validateToken(tokenResponseDto.refreshToken())) {
            throw new IllegalArgumentException("Refresh token is invalid or expired");
        }

        String empId = jwtService.extractId(tokenResponseDto.refreshToken());
        Optional<Employee> emp = employeeDao.findById(UUID.fromString(empId));

        if (emp.isEmpty()) {
            throw new IllegalArgumentException("Employee not found for token");
        }

        String accessToken = jwtService.generateAccessToken(emp.get());
        String refreshToken = jwtService.generateRefreshToken(emp.get());

        TokenResponseDto newTokenResponseDto = new TokenResponseDto(accessToken, refreshToken);

        return new ApiResponse<TokenResponseDto>(HttpStatusCode.CREATED, newTokenResponseDto);
    }

    @Override
    public void createDemoUser() {

        long employeeCount = employeeDao.count();
        System.out.println("Employee count: " + employeeCount);
        if (employeeCount > 0) {
            return;
        }

        Position position = new Position();
        position.setName("Admin");

        Role role = new Role();
        role.setName("ADMIN");

        Department department = new Department();
        department.setName("IT");

        Employee emp = new Employee();
        emp.setUserName("Thiha");
        emp.setEmail("thiha@gmail.com");
        emp.setEmployeeId("001416");

        String rawPassword = "thiha";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        emp.setPassword(encodedPassword);

        emp.setGender(Gender.MALE);
        emp.setDateOfBirth(LocalDate.of(2004, 2, 13));
        emp.setNrcNumber("12/mayaka(N)175637");
        emp.setDateOfEmployment(LocalDate.of(2023, 1, 1));
        emp.setRecordStatus(1);

        emp.setPosition(position); // Set created Position
        emp.setDepartment(department); // No department
        emp.setRoles(Collections.singletonList(role));

        employeeDao.save(emp);
    }
}
