package com.api.ems.service;

import com.api.ems.entity.Employee;
import com.api.ems.exception.ResourceNotFoundException;
import com.api.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public boolean employeeExists(Employee employee) {
        // Check if an employee with the same email (or other unique field) already exists
        return employeeRepository.existsByEmailId(employee.getEmailId());
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        // Check if employee with the given ID exists
        Employee employeeById = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        // Verify emailId matches
        if (!employeeById.getEmailId().equals(employeeDetails.getEmailId())) {
            throw new ResourceNotFoundException("EmailId does not match for employee with id: " + id);
        }
        // Update employee details
        employeeById.setFirstName(employeeDetails.getFirstName());
        employeeById.setLastName(employeeDetails.getLastName());
        return employeeRepository.save(employeeById);
    }

    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }
}
