package com.poc7.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.poc7.FileUploadUtil;

import com.poc7.model.Project;
import com.poc7.model.Student;

import com.poc7.service.ProjectService;
import com.poc7.service.StudentService;

@Controller
public class StudentViewController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private ProjectService projectService;

	@RequestMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "id", "asc", model);
	}

	@RequestMapping(path = { "/createStudentPage", "/edit/{id}" })
	public String createStudent(Model model, @PathVariable(value = "id") Optional<Integer> id) throws RuntimeException {
		if (id.isPresent()) {
			Student student = studentService.getStudentById1(id.get());

			model.addAttribute("student", student);
		} else {
			model.addAttribute("student", new Student());
		}
		return "add-student";
	}

	@RequestMapping(path = "deleteStudent/{id}")
	public String deleteStudentById(Model model, @PathVariable(value = "id") Integer studentId)
			throws RuntimeException {
		studentService.deleteStudentById(studentId);
		return "redirect:/";

	}

	@RequestMapping(path = { "/projects/{id}" })
	public String showProjects(Model model, @PathVariable(value = "id") Integer studentId) {
		List<Project> projects = projectService.getAllProjectsByStudentId(studentId);
		model.addAttribute("projects", projects);
		return "show-projects";
	}

	@RequestMapping(path = { "/createProjectPage/{id}" })
	public String addProject(Model model, @PathVariable(value = "projectId") Optional<Integer> projectId,
			@PathVariable(value = "id") Integer studentId) throws RuntimeException {
		if (projectId.isPresent()) {
			Project project = projectService.getProjectById(projectId.get());
			model.addAttribute("project", project);
			model.addAttribute("studentId", studentId);
		} else {
			model.addAttribute("project", new Project());
			model.addAttribute("studentId", studentId);
		}
		return "add-edit-projects";
	}

	@RequestMapping(path = "/createProject/{id}", method = RequestMethod.POST)
	public String createProject(Project project, @PathVariable(value = "id") Integer studentId)
			throws RuntimeException {
		projectService.createProject(studentId, project);
		return "redirect:/";

	}

	@RequestMapping(path = "/updateProject/{id}/{projectId}", method = RequestMethod.POST)
	public String updateProject1(Project project, @PathVariable(value = "projectId") Integer projectId,
			@PathVariable(value = "id") Integer studentId) throws RuntimeException {
		projectService.updateProject(studentId, projectId, project);
		return "redirect:/";

	}

	@RequestMapping(path = "/createStudent", method = RequestMethod.POST)
	public String createStudent(Student student, @RequestParam("image") MultipartFile multipartFile)
			throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		System.out.println(fileName);
		student.setPhoto(fileName);
		Student savedStudent = studentService.createOrUpdateStudent(student);
		String uploadDir = "students-photos/" + savedStudent.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		return "redirect:/";

	}

	@RequestMapping(path = { "/editProject/{projectId}" })
	public String updateProject(@PathVariable(value = "projectId") Optional<Integer> projectId, Integer studentId,
			Model model) throws RuntimeException {
		if (projectId.isPresent()) {
			Project project = projectService.getProjectById(projectId.get());
			model.addAttribute("project", project);
			model.addAttribute("projectId", project.getStudent().getId());
			model.addAttribute("projectId1", project.getProjectId());
		}
		return "edit-project";
	}

	@RequestMapping(path = "deleteProject/{projectId}")
	public String deleteProjectById(Model model, @PathVariable(value = "projectId") Integer projectId)
			throws RuntimeException {

		Project project = projectService.getProjectById(projectId);
		projectService.deleteProject(projectId, project.getStudent().getId());
		return "redirect:/";

	}

	@RequestMapping(path = "/search-user-by-id", method = RequestMethod.GET)
	public String SearchById(Model model, @RequestParam(value = "id", required = false) Integer id)
			throws RuntimeException {

		boolean b = false;
		if (studentService.getStudentById1(id) == null) {
			b = false;
			model.addAttribute("student", new Student());
		} else {
			b = true;
			model.addAttribute("student", studentService.getStudentById1(id));
			model.addAttribute("project", projectService.getAllProjectsByStudentId(id));
		}
		model.addAttribute("value", b);
		return "search-by-id";
	}

	@RequestMapping(path = "/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortFie,
			@RequestParam("sortDir") String sortDir, Model model) {

		int pageSize = 4;

		Page<Student> page = studentService.findPaginated(pageNo, pageSize, sortFie, sortDir);
		List<Student> students = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortFie);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("students", students);
		return "student-list"; // html page
	}

}
