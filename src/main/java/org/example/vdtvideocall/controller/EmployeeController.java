package org.example.vdtvideocall.controller;

import org.example.vdtvideocall.model.Employee;
import org.example.vdtvideocall.payload.request.EmployeeCreateRequest;
import org.example.vdtvideocall.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/employee")
@Controller
public class EmployeeController {

    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping("create")
    ResponseEntity<Employee> createEmployee(@RequestBody EmployeeCreateRequest employeeCreateRequest) {
        Employee employee = employeeService.create(employeeCreateRequest);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/{id}")
    public String employee(@PathVariable String id, Model model){
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if(employee.isPresent()){
            employeeService.updateEmployeeStatus(employee.get().getId(), Employee.Status.AVAILABLE);
            model.addAttribute("employeeInfo", employee.get());
        }
        return "employee";
    }

}