package com.example.leavemanagement.service;

import com.example.leavemanagement.entity.Employee;
import com.example.leavemanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Add a new employee
    public Employee addEmployee(Employee employee) {

        // Check whether the email already exists
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException(
                    "Employee with this email already exists"
            );
        }

        // Every new employee receives 10 leave days
        employee.setLeaveBalance(10);

        // Save employee in the database
        return employeeRepository.save(employee);
    }

    // View all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Find one employee using employee ID
    public Employee getEmployeeById(Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found with ID: " + id
                        )
                );
    }
}
