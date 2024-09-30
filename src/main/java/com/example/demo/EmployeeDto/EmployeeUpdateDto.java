package com.example.demo.EmployeeDto;

import java.util.List;

public class EmployeeUpdateDto {
	private List<String> userid;
	private UpdateData update;
	
	public UpdateData getUpdate() {
		return update;
	}


	public void setUpdate(UpdateData update) {
		this.update = update;
	}


	public List<String> getUserid() {
		return userid;
	}


	public void setUserid(List<String> userid) {
		this.userid = userid;
	}


	public  class UpdateData{
		private String name;
        private String mobile_number;
        private String pan_num;
        private String manager_id;
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
		public String getManager_id() {
			return manager_id;
		}
		public void setManager_id(String manager_id) {
			this.manager_id = manager_id;
		}
        
        
	}

}
