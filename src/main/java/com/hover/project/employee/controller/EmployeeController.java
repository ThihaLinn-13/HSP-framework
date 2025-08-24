package com.hover.project.employee.controller;

import com.hover.project.employee.dao.EmployeeDao;
import com.hover.project.employee.request.CreateEmployeeRequest;
import com.hover.project.employee.service.EmployeeService;
import com.hover.project.util.type.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping()
    public void createEmployee(){

    }

    @GetMapping()
    public ResponseEntity<ApiResponse<UUID>> getAllEmployee(@RequestBody CreateEmployeeRequest createEmployeeRequest){
        var response = employeeService.createEmployee(createEmployeeRequest);
        return response.buildResponse(response);
    }

    @GetMapping("/{syskey}")
    public void getEmployeeBySyskey(@PathVariable UUID syskey){

    }

    @DeleteMapping("/{syskey}")
    public void  deleteEmployee(@PathVariable UUID syskey){

    }

    @PatchMapping("/{syskey}")
    public void updateEmployee(@PathVariable UUID syskey, @RequestBody Map<String,Object> updateFields){

    }

}
