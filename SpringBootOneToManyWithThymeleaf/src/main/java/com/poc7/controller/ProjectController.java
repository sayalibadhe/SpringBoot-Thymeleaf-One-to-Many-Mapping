package com.poc7.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc7.ResultModel;
import com.poc7.model.Project;

import com.poc7.service.ProjectService;

@RestController
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping("/getAllProjectTest/{id}")
	public ResultModel getAllUsersTest(@PathVariable(value = "id") Integer id) {
		return projectService.getAllProjectsByStudentIdTest(id);
	}

	@DeleteMapping("/deleteProjectByStudentId/{projectId}")
	public ResultModel deleteUserByIdTest(@PathVariable(value = "projectId") Integer id) {
		Project project = projectService.getProjectById(id);
		return projectService.deleteProjectTest(id, project.getStudent().getId());

	}

	@PostMapping("/createProjectTest/{id}")
	public ResultModel createProjecttest(@Valid @RequestBody Project project,
			@PathVariable(value = "id") Integer studentId) {
		return projectService.addProject(studentId, project);

	}
}
