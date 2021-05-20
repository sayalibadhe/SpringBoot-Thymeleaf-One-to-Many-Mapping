package com.poc7.test;

import java.io.File;
import java.io.FileInputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc7.ResultModel;
import com.poc7.model.Project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ControllerTestMethods {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	ObjectMapper objm = new ObjectMapper();

	@BeforeAll
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).dispatchOptions(true).build();
	}
	
	@Test
	public void createStudentTest() throws Exception {
		File file = new File("C:\\Users\\user\\Desktop\\sayali.jpeg");
		FileInputStream fileinput1 = new FileInputStream(file);
		MockMultipartFile multipartfile = new MockMultipartFile("image", file.getName(), "multipart/form-data",
				fileinput1);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/createStudent").file(multipartfile).param("id", "7")
				.param("firstName", "Sayali").param("lastName", "Badhe").param("emailId", "tiwariaakash@gmail.com")
				.param("contact", "1234567878").param("skills", "javatest").contentType(MediaType.MULTIPART_FORM_DATA))
				.andDo(print()).andExpect(redirectedUrl("/"));

	}

	@Test
	public void getAllStudents() throws Exception {
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/getAllUserTest").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String resultContext = result.getResponse().getContentAsString();
		ResultModel response = objm.readValue(resultContext, ResultModel.class);
		Assertions.assertTrue(response.isStatus() == Boolean.TRUE);
		Assertions.assertEquals("Success", response.getProgressMessage());
	}

	@Test

	public void getStudentByIdTest() throws Exception {
		int id = 9;
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/getUserById/" + id).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();

		ResultModel response = objm.readValue(resultContent, ResultModel.class);
		Assertions.assertTrue(response.isStatus() == Boolean.TRUE);
		Assertions.assertEquals("Success", response.getProgressMessage());
	}

	@Test

	public void deleteStudentTest() throws Exception {
		int id = 6;

		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders.delete("/deletUser/" + id).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		ResultModel response = objm.readValue(resultContent, ResultModel.class);
		Assertions.assertTrue(response.isStatus() == Boolean.TRUE);
		Assertions.assertEquals("Success", response.getProgressMessage());
	}

	@Test
	public void getAllProjects() throws Exception {
		int id = 1;
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/getAllProjectTest/" + id)
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String resultContext = result.getResponse().getContentAsString();
		ResultModel response = objm.readValue(resultContext, ResultModel.class);
		Assertions.assertTrue(response.isStatus() == Boolean.TRUE);
		Assertions.assertEquals("Success", response.getProgressMessage());
	}

	@Test

	public void deleteProjectsTest() throws Exception {
		int id = 2;

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete("/deleteProjectByStudentId/6")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		ResultModel response = objm.readValue(resultContent, ResultModel.class);
		Assertions.assertTrue(response.isStatus() == Boolean.TRUE);
		Assertions.assertEquals("Success", response.getProgressMessage());
	}

	@Test
	public void createProject() throws Exception {
		Project project = new Project();
		project.setProjectName("Post Comment Rest API");
		project.setTechnology("Java,Spring Boot,Rest Web Services");
		String JsonRequest = objm.writeValueAsString(project);
		MvcResult result = mockMvc
				.perform(
						post("/createProjectTest/1").content(JsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		String resultContext = result.getResponse().getContentAsString();
		ResultModel response = objm.readValue(resultContext, ResultModel.class);
		Assertions.assertTrue(response.isStatus() == Boolean.TRUE);
		Assertions.assertEquals("Success", response.getProgressMessage());
	}
}
