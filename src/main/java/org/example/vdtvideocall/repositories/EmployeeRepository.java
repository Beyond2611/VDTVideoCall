package org.example.vdtvideocall.repositories;

import org.example.vdtvideocall.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Optional<Employee> findByUsername(String username);
    Optional<Employee> findFirstByStatus(Employee.Status status);
}
