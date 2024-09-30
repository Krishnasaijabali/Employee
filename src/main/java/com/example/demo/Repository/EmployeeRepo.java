package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {
	

	@Query("SELECT e FROM Employee e WHERE e.userid = :userid")
	Employee findRow(@Param("userid") String userid);
	
	@Query("SELECT e.manager_id FROM Employee e WHERE e.userid = :userid")
    String findMangerId(@Param("userid") String userid);
	
	
    @Query("SELECT e.userid FROM Employee e WHERE e.userid = :userid")
    String findUserId(@Param("userid") String userid);

	@Query("SELECT e FROM Employee e WHERE e.userid = :userid")
	List<Employee> findEmployeesByUserId(@Param("userid") String mobileNumber);

	@Query("SELECT e FROM Employee e WHERE e.mobile_number = :mobileNumber")
	List<Employee> findEmployeesByMobileNumber(@Param("mobileNumber") String mobileNumber);

	@Query("SELECT e FROM Employee e WHERE e.manager_id = :managerId")
	List<Employee> findEmployeesByManagerId(@Param("managerId") String managerId);

	@Transactional
	@Modifying
	@Query("delete from Employee e where e.mobile_number =:num")
	void deleteEmployeebyMobNum(@Param("num") String num);

	
}
