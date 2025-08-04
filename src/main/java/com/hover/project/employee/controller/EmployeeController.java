package com.hover.project.employee.controller;

import com.hover.project.employee.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @PostMapping()
    public void createEmployee(){

    }

    @GetMapping()
    public ResponseEntity<String> getAllEmployee(){
        return ResponseEntity.ok("Hello security");
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
