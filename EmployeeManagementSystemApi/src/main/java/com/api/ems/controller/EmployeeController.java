package com.api.ems.controller;

import com.api.ems.entity.Employee;
import com.api.ems.response.ResponseDTO;
import com.api.ems.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/employee")
@Tag(name = "Employee Management", description = "APIs for managing employees") // Swagger group tag
public class EmployeeController {


	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
   }

    /**
	 * Get all employees
	 * @return List of all employees with status and message
	 */
	@Operation(
			summary = "Get All Employees",
			description = "Retrieve a list of all employees from the database. If no employees are found, it will return a message indicating that no data is available."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ResponseDTO.class))),
			@ApiResponse(responseCode = "204", description = "No employees found",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/employees")
	public ResponseEntity<ResponseDTO<List<Employee>>> getAllEmployees() {
		List<Employee> allEmployees = employeeService.getAllEmployees();
		if (allEmployees.isEmpty()) {
			return ResponseDTO.prepare("No employees found", HttpStatus.NO_CONTENT.value());
		}
		return ResponseDTO.prepare("List of all employees", HttpStatus.OK.value(), allEmployees);
	}


	/**
	 * Create a new employee
	 * @param employee Employee object containing details to be created
	 * @return Created employee object with status and message
	 */
	@Operation(summary = "Create Employee", description = "Create a new employee")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully created the employee",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input provided or employee already exists",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ResponseDTO.class)))
	})
	@PostMapping
	public ResponseEntity<ResponseDTO<Employee>> createEmployee(@RequestBody Employee employee) {
		if (employeeService.employeeExists(employee)) {
			return ResponseDTO.prepare("Employee already exists", HttpStatus.BAD_REQUEST.value());
		}
		Employee createdEmployee = employeeService.createEmployee(employee);
		return ResponseDTO.prepare("Employee created successfully", HttpStatus.OK.value(), createdEmployee);
	}


	/**
	 * Get an employee by ID
	 * @param id Employee ID
	 * @return Employee details if found, or a 404 error if not
	 */
	@Operation(summary = "Get Employee By ID", description = "Retrieve an employee by their ID")
	@ApiResponse(responseCode = "200", description = "Successfully retrieved the employee")
	@ApiResponse(responseCode = "404", description = "Employee not found")
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<Employee>> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeService.getEmployeeById(id);
		return ResponseDTO.prepare("Employee details retrieved successfully", HttpStatus.OK.value(), employee);
	}

	/**
	 * Update an employee by ID
	 * @param id Employee ID
	 * @param employeeDetails Updated employee details
	 * @return Updated employee object with status and message
	 */
	@Operation(summary = "Update Employee", description = "Update an existing employee by their ID and emailId")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully updated the employee",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Employee not found with the given ID or emailId",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ResponseDTO.class)))
	})
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO<Employee>> updateEmployee(
			@PathVariable Long id,
			@RequestBody Employee employeeDetails) {
		Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
		return ResponseDTO.prepare("Employee updated successfully", HttpStatus.OK.value(), updatedEmployee);
	}



	/**
	 * Delete an employee by ID
	 * @param id Employee ID
	 * @return Confirmation of deletion with status and message
	 */
	@Operation(summary = "Delete Employee", description = "Delete an existing employee by their ID")
	@ApiResponse(responseCode = "200", description = "Successfully deleted the employee")
	@ApiResponse(responseCode = "404", description = "Employee not found")
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDTO<Map<String, Boolean>>> deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseDTO.prepare("Employee deleted successfully", HttpStatus.OK.value(), response);
	}
}
