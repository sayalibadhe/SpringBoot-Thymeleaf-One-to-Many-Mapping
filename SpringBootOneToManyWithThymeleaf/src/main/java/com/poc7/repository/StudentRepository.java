package com.poc7.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc7.model.Student;


@Repository 
public interface StudentRepository extends JpaRepository<Student, Integer>{

	public Student findStudentById(Integer id);
	
	
}
