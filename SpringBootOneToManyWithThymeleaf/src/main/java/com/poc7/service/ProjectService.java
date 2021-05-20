package com.poc7.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.poc7.ResultModel;
import com.poc7.model.Project;
import com.poc7.model.Student;

import com.poc7.repository.ProjectRepository;
import com.poc7.repository.StudentRepository;

@Service
public class ProjectService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ProjectRepository projectRepository;

	public Project createProject(Integer studentId, Project project) throws RuntimeException {
		Optional<Student> student = studentRepository.findById(studentId);
		if (student.isPresent()) {
			Student student1 = studentRepository.findStudentById(studentId);
			project.setStudent(student1);
			return projectRepository.save(project);
		} else {
			throw new RuntimeException("Student with ID: " + studentId + " was not found");
		}
	}

	public Project getProjectById(Integer projectId) throws RuntimeException {
		Optional<Project> project = projectRepository.findById(projectId);
		if (project.isPresent()) {
			return project.get();
		} else {
			throw new RuntimeException("Project with ID: " + projectId + " was not Found");
		}
	}

	public List<Project> getAllProjectsByStudentId(Integer studentId) {
		List<Project> project = projectRepository.findByStudentId(studentId);
		if (project.size() > 0) {
			return project;
		} else {
			return new ArrayList<Project>();
		}
	}

	public Project updateProject(Integer studentId, Integer projectId, Project project) throws RuntimeException {
		if (!studentRepository.existsById(studentId)) {
			throw new RuntimeException("Student with ID: " + studentId + " was not found");
		}
		return projectRepository.findById(projectId).map(newProject -> {
			newProject.setProjectName(project.getProjectName());
			newProject.setTechnology(project.getTechnology());

			return projectRepository.save(newProject);
		}).orElseThrow(() -> new RuntimeException("Project with ID: " + projectId + " was not Found"));
	}

	public void deleteProject(Integer projectId, Integer studentId) throws RuntimeException {
		Optional<Project> project = projectRepository.findByProjectIdAndStudentId(projectId, studentId);
		if (project.isPresent()) {
			projectRepository.deleteById(projectId);
		} else {
			throw new RuntimeException(
					"Student with ID: " + studentId + " and Subject with ID: " + projectId + "was not found");
		}
	}

	// test methods

	public ResultModel getAllProjectsByStudentIdTest(Integer id) {
		List<Project> project = projectRepository.findByStudentId(id);
		if (project.size() == 0) {
			return new ResultModel("No Students were Found", Boolean.FALSE, "Failed");
		} else {
			return new ResultModel("Number Students were Found are " + project.size(), Boolean.TRUE, "Success");
		}
	}

	public ResultModel deleteProjectTest(Integer projectId, Integer studentId) {
		Optional<Project> project = projectRepository.findByProjectIdAndStudentId(projectId, studentId);
		if (project.isPresent()) {
			projectRepository.deleteById(projectId);
			return new ResultModel("Project with  ID: " + projectId + " deleted ", Boolean.TRUE, "Success");
		} else {
			return new ResultModel("No Project with ID: " + projectId + " are found ", Boolean.FALSE, "Failed");
		}
	}

	public ResultModel addProject(Integer studentId, Project project) {
		Optional<Student> student = studentRepository.findById(studentId);
		if (student.isPresent()) {
			Student student1 = studentRepository.findStudentById(studentId);
			project.setStudent(student1);
			return new ResultModel("User with Name : " + project.getProjectName() + " saved ", Boolean.TRUE, "Success");
		} else {
			return new ResultModel("No Project saved  ", Boolean.FALSE, "Failed");
		}

	}
}
