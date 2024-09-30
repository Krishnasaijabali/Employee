package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.EmployeeDto.EmployeeDto;
import com.example.demo.EmployeeDto.EmployeeUpdateDto;
import com.example.demo.EmployeeDto.PostmanResponse;
import com.example.demo.Entity.Employee;
import com.example.demo.Repository.EmployeeRepo;


@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo employeeRepo;

	

	public String setCurrentTime() {
		// Get the current date and time
		LocalDateTime now = LocalDateTime.now();

		// Define the format for the time, excluding seconds
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		// Format the current time as a string
		return now.format(formatter);
	}

	// method for converting userid to randomUUID
	public static String convertToUUID() {
		UUID newUuid = UUID.randomUUID();
		return newUuid.toString();

	}

	// method for validating mobile number
	public boolean verifyMobileNumber(String mobileNumber) {

		// Regular expression to match +91xxxxxxxxxx, 0xxxxxxxxxx, or 10-digit number
		String regex = "^(\\+91[0-9]{10}|+0[0-9]{10}|[0-9]{10})$";

		// Check if the mobile number matches the regex pattern return mobileNumber
		return mobileNumber != null && mobileNumber.matches(regex);
	}

	// method for save details
	public PostmanResponse saveDetails(Employee employee) {
		PostmanResponse postmanResponse=new PostmanResponse();
		PostmanResponse.Details details = postmanResponse.new Details();
		// validating Name
		if (employee.getName() == null || employee.getName().isEmpty()||!employee.getName().matches("^[A-Za-z]+$")) {
			postmanResponse.setMessage("invalid username");
			return postmanResponse;
		}
		// validating mobile number
		if (!verifyMobileNumber(employee.getMobile_number())) {
			postmanResponse.setMessage("invalid mobile number");
			return postmanResponse;
		}

		String mobileNumber = employee.getMobile_number();

		// Remove prefix if present
		if (mobileNumber.startsWith("+91") && mobileNumber.length() == 13) {
			mobileNumber = mobileNumber.substring(3);
		} else if (mobileNumber.startsWith("0") && mobileNumber.length() == 11) {
			mobileNumber = mobileNumber.substring(1);
		}

		employee.setMobile_number(mobileNumber);

		String panRegex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
		String pan = employee.getPan_num();
		pan = pan.toUpperCase();
		// validating pan number
		if (!pan.matches(panRegex)) {
			postmanResponse.setMessage("invalid pan number");
			return postmanResponse;
		}
		employee.setPan_num(pan);
		employee.setUserid(convertToUUID());
		System.out.println(employee);
		if (employeeRepo.save(employee) != null) {
			employee.setIsactive(true);
			employee.setCreated_date(setCurrentTime());
			details.setMobile_number(employee.getMobile_number());
			details.setName(employee.getName());
			details.setPan_num(employee.getPan_num());
			postmanResponse.setDetails(details);
			employeeRepo.save(employee);
		}
		postmanResponse.setMessage("employee details saved successfully");
		return postmanResponse;
	}

	// delete method
	public String deleteEmployeeByUserId(EmployeeDto employee) {

		if (employee.userid != null) {
			employeeRepo.deleteById(employee.userid);
			return "employee deleted succesfully";
		} else if (employee.mobile_number != null) {
			employeeRepo.deleteEmployeebyMobNum(employee.mobile_number);
			return "employee deleted succesfully";
		} else {
			return "enter either mobile number or userid";
		}

	}

	// get method
	public List<Employee> getAllEmployee(EmployeeDto employee) {
		// TODO Auto-generated method stub

		if (employee.getUserid() == null && employee.getMobile_number() != null && employee.getManager_id() == null) {
			return employeeRepo.findEmployeesByMobileNumber(employee.getMobile_number());
		} else if (employee.getUserid() == null && employee.getMobile_number() == null
				&& employee.getManager_id() != null) {
			return employeeRepo.findEmployeesByManagerId(employee.getManager_id());
		} else if (employee.getUserid() != null && employee.getMobile_number() == null
				&& employee.getManager_id() == null) {
			return employeeRepo.findEmployeesByUserId(employee.getUserid());
		}
		
		return employeeRepo.findAll();

	}

	// update method
	public String updateEmployee(EmployeeUpdateDto employe) {
		// TODO Auto-generated method stub
		String str = "";
		Employee employee = new Employee();
		List<String> userids = employe.getUserid();
		// if more userids provided
		if (userids.size() > 1) {
			EmployeeUpdateDto.UpdateData updatedata = employe.getUpdate();
			if ((updatedata.getName() != null || updatedata.getMobile_number() != null
					|| updatedata.getPan_num() != null)) {
				str += " these keys can be updated on a individual basis only and not in bulk. .\n";
			} else if (updatedata.getManager_id() == null) {
				str += "please provide manager id \n";
			} else {
				for (String userid : userids) {
					if (employeeRepo.findUserId(userid) != null) {
						employee = employeeRepo.findRow(userid);
						if (employee.isIsactive()) {
							if (employee.getManager_id() == null) {
								employee.setManager_id(updatedata.getManager_id());
								employee.setUpdated_date(setCurrentTime());
							} else {
								if (!updatedata.getManager_id().equals(employee.getManager_id())) {
									employee.setIsactive(false);
									employee.setUpdated_date(setCurrentTime());
									Employee emp1 = new Employee();
									emp1.setName(employee.getName());
									emp1.setMobile_number(employee.getMobile_number());
									emp1.setPan_num(employee.getPan_num());
									emp1.setManager_id(updatedata.getManager_id());
									saveDetails(emp1);
									str += "new row added for the updated manager id \n";
								} else {
									str += "same manager id no change \n";
								}
							}
						} else {
							str += "Row is inactive given user id " + userid + "\n";
						}
						employeeRepo.save(employee);
					} else {
						str += "there is no user id with  " + userid + " \n";
					}
				}
			}
		} else if (userids.size() == 1) {
			EmployeeUpdateDto.UpdateData updatedata = employe.getUpdate();
			if (employeeRepo.findUserId(userids.get(0)) != null) {
				employee = employeeRepo.findRow(userids.get(0));
				if (updatedata.getManager_id() == null) {
					str += "  please provide manager id  \n";
				} else {
					boolean invaliddetails = false;
					if (updatedata.getMobile_number() != null && verifyMobileNumber(updatedata.getMobile_number())) {
						if (updatedata.getMobile_number().startsWith("+91")
								&& updatedata.getMobile_number().length() == 13) {
							updatedata.setMobile_number(updatedata.getMobile_number().substring(3));
						} else if (updatedata.getMobile_number().startsWith("0")
								&& updatedata.getMobile_number().length() == 11) {
							updatedata.setMobile_number(updatedata.getMobile_number().substring(1));
						}
						employee.setMobile_number(updatedata.getMobile_number());
					} else if (updatedata.getMobile_number() != null) {
						invaliddetails = true;
						str += "  invalid mobile number   \n";
					}
					if (updatedata.getName() != null && !updatedata.getName().isEmpty()) {
						employee.setName(updatedata.getName());
					} else if (updatedata.getName() != null) {
						invaliddetails = true;
						str += "  invalid name  \n";
					}
					String panRegex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
					if (updatedata.getPan_num() != null && updatedata.getPan_num().matches(panRegex)) {
						employee.setPan_num(updatedata.getPan_num());
					} else if (updatedata.getPan_num() != null) {
						invaliddetails = true;
						str += "    invalid pan number    \n ";
					}
					if (!invaliddetails) {
						if (employee.isIsactive()) {
							if (employee.getManager_id() == null) {
								employee.setManager_id(updatedata.getManager_id());
								employee.setUpdated_date(setCurrentTime());
							} else {
								if (!updatedata.getManager_id().equals(employee.getManager_id())) {
									employee.setIsactive(false);
									employee.setUpdated_date(setCurrentTime());
									Employee emp1 = new Employee();
									emp1.setName(employee.getName());
									emp1.setMobile_number(employee.getMobile_number());
									emp1.setPan_num(employee.getPan_num());
									emp1.setManager_id(updatedata.getManager_id());
									saveDetails(emp1);
									str += "new row added for the updated manager id \n";
									employeeRepo.save(employee);
								} else {
									str += "same manager id no change \n";
								}
							}

						} else {
							str += "Row is inactive given user id " + userids.get(0) + "\n";
						}
					}
				}

			} else {
				str += "there is no user id with  " + userids.get(0) + " \n";
			}
		}
		return str;
	}
}
