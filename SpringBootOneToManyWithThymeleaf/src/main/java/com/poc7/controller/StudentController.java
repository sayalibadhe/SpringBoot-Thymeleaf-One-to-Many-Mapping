package com.poc7.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.poc7.ResultModel;
import com.poc7.service.StudentService;

@RestController
public class StudentController{
	
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/getAllUserTest")
	 public ResultModel getAllUsersTest() {
	 	return studentService.getAllStudentsTest();
	 }
	 
	 @GetMapping("/getUserById/{id}")
	 public ResultModel getUserByIdTest(@PathVariable (value="id") Integer id) {
	 	return studentService.getStudentByIdTest(id);
	 }
	 
	
	 @DeleteMapping("/deletUser/{id}")
	 public ResultModel deleteUserByIdTest(@PathVariable (value="id") Integer id) {
	 	return studentService.deleteStudentByIdTest(id);
	 }
}