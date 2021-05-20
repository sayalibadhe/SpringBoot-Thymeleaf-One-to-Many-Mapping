package com.poc7.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc7.model.Project;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{

	
	
	public Optional<Project> findByProjectIdAndStudentId(Integer projectId, Integer studentId);
	
	List<Project>  findByStudentId(Integer studentId);
	
}
