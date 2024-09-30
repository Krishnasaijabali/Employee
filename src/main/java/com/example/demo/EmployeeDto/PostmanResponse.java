package com.example.demo.EmployeeDto;



public class PostmanResponse {
	private String message;
	private Details details;
	
	
	public Details getDetails() {
		return details;
	}


	public void setDetails(Details details) {
		this.details = details;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public class Details{
		private String name;
		private String mobile_number;
		private String pan_num;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getMobile_number() {
			return mobile_number;
		}
		public void setMobile_number(String mobile_number) {
			this.mobile_number = mobile_number;
		}
		public String getPan_num() {
			return pan_num;
		}
		public void setPan_num(String pan_num) {
			this.pan_num = pan_num;
		}
		
	}
	

}
