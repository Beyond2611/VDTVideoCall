package org.example.vdtvideocall.services;

import org.example.vdtvideocall.model.Employee;
import org.example.vdtvideocall.payload.request.EmployeeCreateRequest;
import org.example.vdtvideocall.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getAvailableEmployee() {
        return  employeeRepository.findFirstByStatus(Employee.Status.AVAILABLE);
    }

    public Optional<Employee> getEmployeebyUsername(String username) {
        return employeeRepository.findByUsername(username);
    }
    public void updateEmployeeStatus(String id, Employee.Status newStatus) {
        Optional<Employee> Oemployee = employeeRepository.findById(id);
        if(Oemployee.isPresent()) {
            Employee employee = Oemployee.get();
            employee.setStatus(newStatus);
            employeeRepository.save(employee);
            return;
        }
    }
    public Employee create(EmployeeCreateRequest input) {
        Employee tempEmployee = new Employee();
        tempEmployee.setUsername(input.getUsername());
        tempEmployee.setStatus(Employee.Status.AVAILABLE);
        return employeeRepository.save(tempEmployee);
    }
}
