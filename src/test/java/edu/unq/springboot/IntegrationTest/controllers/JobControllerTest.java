package edu.unq.springboot.IntegrationTest.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.unq.springboot.controllers.JobController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.unq.springboot.models.CreateJobRequestBody;
import edu.unq.springboot.models.Job;
import edu.unq.springboot.models.User;
import edu.unq.springboot.service.JobService;
import edu.unq.springboot.service.UserService;

@WebMvcTest(controllers = JobController.class)
public class JobControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
    private UserService userService;
	@MockBean
    private JobService jobService;
	private User usuarioDos = new User("DosSantos", "pass", "fname", "lname", "correo");
	private Job trabajo = new Job(usuarioDos, "Titulo", "Descripcion", LocalDate.parse("2010-10-20"), LocalDate.parse("2015-08-10"), "www.link.com");

	@Test
	void whenValidInput_thenEditJobReturns200() throws Exception {
        String json = mapper.writeValueAsString(this.trabajo);
        mvc.perform(post("/jobs/edit")
        	.param("username", this.usuarioDos.getUsername())
        	.param("id", "44")
        	.content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void whenValidInput_thenCreateJobReturns200() throws Exception {
        CreateJobRequestBody bd = new CreateJobRequestBody("Jose123", "titulo", "desc", "2010-01-01", "2010-01-01", "www.link.com");
        String json = mapper.writeValueAsString(bd);
        mvc.perform(post("/jobs/create").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void whenExistingUser_thenGetUserJobsReturnsAListOfJobsAsJSON() throws Exception {
    	List<Job> jobDataAsList = new ArrayList<Job>();
    	jobDataAsList.add(trabajo);
    	given(jobService.findByUsername(usuarioDos.getUsername())).willReturn(jobDataAsList);

    	MvcResult mvcResult = mvc.perform(get("/jobs").param("username", usuarioDos.getUsername()))
    		.andExpect(status().isOk())
    		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
    		.andReturn();
    	String expectedResponseBody = mapper.writeValueAsString(jobDataAsList);
    	String actualResponseBody = mvcResult.getResponse().getContentAsString();
    	assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
	}
}
