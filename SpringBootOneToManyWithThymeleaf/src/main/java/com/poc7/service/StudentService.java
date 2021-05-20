package com.poc7.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.poc7.ResultModel;
import com.poc7.model.Student;

import com.poc7.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public List<Student> getallStudents() {
		List<Student> result = (List<Student>) studentRepository.findAll();
		if (result.size() > 0) {
			return result;
		} else {
			return new ArrayList<Student>();
		}
	}

	public Student getStudentbyId(Integer id) throws RuntimeException {
		Student student = studentRepository.findStudentById(id);

		if (student != null) {
			return student;
		} else
			throw new RuntimeException("No record found with this id");

	}

	public ResponseEntity<?> deleteStudentById(Integer id) throws RuntimeException {
		return studentRepository.findById(id).map(student -> {
			studentRepository.delete(student);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new RuntimeException("No student found"));
	}

	public Student getStudentById1(Integer studentId) throws RuntimeException {
		Optional<Student> student = studentRepository.findById(studentId);
		if (student.isPresent()) {
			return student.get();
		} else {
			throw new RuntimeException("Student with ID: " + studentId + " was not Found");
		}
	}

	public Student createOrUpdateStudent(Student student) {
		if (student.getId() == null) {
			student = studentRepository.save(student);
			return student;
		} else {
			Optional<Student> student1 = studentRepository.findById(student.getId());
			if (student1.isPresent()) {
				Student newStudent = new Student();
				newStudent.setFirstName(student.getFirstName());
				newStudent.setLastName(student.getLastName());
				newStudent.setContact(student.getContact());
				newStudent.setEmailId(student.getEmailId());
				newStudent.setSkills(student.getSkills());
				newStudent.setPhoto(student.getPhoto());
				newStudent = studentRepository.save(student);
				return newStudent;
			} else {
				student = studentRepository.save(student);
				return student;
			}
		}

	}

	public Page<Student> findPaginated(int pageNo, int pageSize, String sortFie, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortFie).ascending()
				: Sort.by(sortFie).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.studentRepository.findAll(pageable);
	}

	// test methods
	public ResultModel getAllStudentsTest() {
		List<Student> student = studentRepository.findAll();
		if (student.size() == 0) {
			return new ResultModel("No Students were Found", Boolean.FALSE, "Failed");
		} else {
			return new ResultModel("Number Students were Found are " + student.size(), Boolean.TRUE, "Success");
		}
	}

	public ResultModel getStudentByIdTest(Integer id) {
		Student user = studentRepository.findStudentById(id);
		if (user != null)
			return new ResultModel(user.getFirstName() + " with ID: " + id + " was found", Boolean.TRUE, "Success");
		else {
			return new ResultModel("No User with Id: " + id + " was found", Boolean.FALSE, "Failed");
		}
	}

	public ResultModel deleteStudentByIdTest(Integer id) {
		Student user = studentRepository.findStudentById(id);
		if (user != null) {
			studentRepository.deleteById(id);
			return new ResultModel("User with  ID: " + id + " deleted ", Boolean.TRUE, "Success");
		} else {
			return new ResultModel("No User with ID: " + id + " are found ", Boolean.FALSE, "Failed");
		}
	}
}
