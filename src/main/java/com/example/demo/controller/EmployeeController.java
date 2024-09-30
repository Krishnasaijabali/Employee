package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.EmployeeDto.EmployeeDto;
import com.example.demo.EmployeeDto.EmployeeUpdateDto;
import com.example.demo.EmployeeDto.PostmanResponse;
import com.example.demo.Entity.Employee;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/Employee")
public class EmployeeController {
   
	
	@Autowired
	private EmployeeService employeeService;
	
	

	@PostMapping("/addEmployee")
	public PostmanResponse postDetails(@RequestBody Employee employee) {
		System.out.println(employeeService);

		return employeeService.saveDetails(employee);
	}

	@PostMapping("/getusers")
	public List<Employee> getAllEmployees(@RequestBody EmployeeDto employee) {
		System.out.println(employeeService);
		return employeeService.getAllEmployee(employee);
	}

	@PostMapping("/deleteEmployee")
	public String deleteEmployee(@RequestBody EmployeeDto userid) {
		try {
			return employeeService.deleteEmployeeByUserId(userid);

		} catch (Exception e) {
			return "Error deleting employee: " + e.getMessage();
		}
	}

	@PostMapping("/updateEmployee")
	public String updateEmployee(@RequestBody EmployeeUpdateDto employees) {
		return employeeService.updateEmployee(employees);
	}

}
