package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table
public class Employee {
	
	public Employee() {
		
	}
	
	public Employee(String created_date, String updated_date, boolean isactive, String userid, String name,
			String mobile_number, String pan_num, String manager_id) {
		super();
		this.created_date = created_date;
		this.updated_date = updated_date;
		this.isactive = isactive;
		this.userid = userid;
		this.name = name;
		this.mobile_number = mobile_number;
		this.pan_num = pan_num;
		this.manager_id = manager_id;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
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
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}
	
	public boolean isIsactive() {
		return isactive;
	}
	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
	public void setPan_num(String pan_num) {
		this.pan_num = pan_num;
	}
	
	@Column
	private String created_date;
	@Column
	private String updated_date;
	@Column
	private boolean isactive;


	@Id
	private String userid;
	@Column
	private String name;
	@Column(name = "mobile_number")
	private String mobile_number;
	@Column
	private String pan_num;
	@Column(name = "manager_id")
	private String manager_id;
	@Column
	
	
	
	public String getManager_id() {
		return manager_id;
	}
	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}
	
	
}
