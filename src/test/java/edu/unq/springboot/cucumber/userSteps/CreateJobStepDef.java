package edu.unq.springboot.cucumber.userSteps;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.unq.springboot.models.CreateJobRequestBody;
import edu.unq.springboot.models.Job;
import edu.unq.springboot.models.User;
import edu.unq.springboot.service.JobService;
import edu.unq.springboot.service.UserService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@WebMvcTest
@RunWith(SpringRunner.class)
public class CreateJobStepDef {
    @Autowired
    private MockMvc mvc;
    @Autowired @MockBean
    private UserService userService;
    @Autowired @MockBean
    private JobService jobService;
    private ResultActions action;
    private String jsonObject;
    @Autowired
	private ObjectMapper mapper;

    @When("A user request to create a new job")
    public void request_to_create_a_new_job() throws Exception {
        CreateJobRequestBody bd = new CreateJobRequestBody("Jose123", "titulo", "desc", "2010-01-01",
        		"2010-01-01", "https://www.mercadolibre.com.ar/", "http://img.us", 1);
        String json = this.mapper.writeValueAsString(bd);
        action = mvc.perform(post("/jobs/create").content(json).contentType(MediaType.APPLICATION_JSON));
    }
    
    @When("Request to get jobs of user 'user0'")
    public void request_to_get_jobs_of_user0() throws Exception {
    	User usuarioDos = new User("DosSantos", "pass", "fname", "lname", "correo");
		Job trabajo = new Job(usuarioDos, "Titulo", "Descripcion", LocalDate.parse("2010-10-20"),
				LocalDate.parse("2015-08-10"), "www.link.com", "http://img.us", 1);
    	List<Job> jobDataAsList = new ArrayList<Job>();
    	jobDataAsList.add(trabajo);
    	given(jobService.findByUsername(usuarioDos.getUsername())).willReturn(jobDataAsList);

    	action = mvc.perform(get("/jobs").param("username", usuarioDos.getUsername()));
    }
    
    @When("A user request to create a new job with invalid date '2010-31-01'")
    public void request_create_new_job_invalid_date() throws Exception {
        CreateJobRequestBody bd = new CreateJobRequestBody("Jose123", "titulo", "desc", "2010-31-01",
        		"2010-01-01", "www.link.com", "http://img.us", 1);
        this.jsonObject = this.mapper.writeValueAsString(bd);
    }
    
    @Then("The new job is not saved and an error is produced")
    public void create_job_error_response() throws Exception {
    	assertThatThrownBy(() -> mvc.perform(post("/jobs/create")
    			.content(this.jsonObject)
    			.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isInternalServerError()))
    	.hasMessageContaining("Invalid value");
    }
    
    @Then("List of 'user0' jobs is received")
    public void get_jobs_response_ok() throws Exception {
        action.andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string(containsString("Descripcion")));
    }
    
    @Then("The new job is created and saved")
    public void response_ok() throws Exception {
        action.andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    //  ordering jobs function tests

    @When("The user request to retrieve they jobs ordered by priority")
    public void theUserRequestToRetrieveTheyJobsOrderedByPriority() throws Exception {
        User user = new User("username", "pass", "fname", "lname", "email");
        Job job1 = new Job(user, "job1", "desc", LocalDate.parse("2010-10-20"),
                LocalDate.parse("2015-08-10"), "www.link.com", "http://img.us", 2);
        Job job2 = new Job(user, "job2", "desc", LocalDate.parse("2010-10-20"),
                LocalDate.parse("2015-08-10"), "www.link.com", "http://img.us", 3);
        Job job3 = new Job(user, "job3", "desc", LocalDate.parse("2010-10-20"),
                LocalDate.parse("2015-08-10"), "www.link.com", "http://img.us", 1);

        List<Job> jobDataAsList = new ArrayList<Job>();
        jobDataAsList.add(job1);
        jobDataAsList.add(job2);
        jobDataAsList.add(job3);

        given(jobService.findByUsernameOrderedByPriority(user.getUsername())).willReturn(jobDataAsList);

        action = mvc.perform(get("/jobs").param("username", user.getUsername()).param("sortBy", "priority"));

    }

    @Then("The received jobs should be ordered by priority")
    public void theReceivedJobsShouldBeOrderedByPriority() throws Exception {
        action.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("desc")));
    }

    @When("The user request to retrieve they jobs ordered by date")
    public void theUserRequestToRetrieveTheyJobsOrderedByDate() throws Exception {
        User user = new User("username", "pass", "fname", "lname", "email");
        Job job1 = new Job(user, "job1", "desc", LocalDate.parse("2010-10-20"),
                LocalDate.parse("2015-08-10"), "www.link.com", "http://img.us", 2);
        Job job2 = new Job(user, "job2", "desc", LocalDate.parse("2010-10-20"),
                LocalDate.parse("2015-08-10"), "www.link.com", "http://img.us", 3);

        List<Job> jobDataAsList = new ArrayList<Job>();
        jobDataAsList.add(job1);
        jobDataAsList.add(job2);

        given(jobService.findByUsernameOrderedByDate(user.getUsername())).willReturn(jobDataAsList);

        action = mvc.perform(get("/jobs").param("username", user.getUsername()).param("sortBy", "date"));

    }

    @Then("The received jobs should be ordered by date")
    public void theReceivedJobsShouldBeOrderedByDate() throws Exception {
        action.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("desc")));

    }

    @When("The user request to retrieve their jobs with a non existing filter")
    public void theUserRequestToRetrieveTheirJobsWithANonExistingFilter() throws Exception {
        User user = new User("username", "pass", "fname", "lname", "email");
        Job job1 = new Job(user, "job1", "desc", LocalDate.parse("2010-10-20"),
                LocalDate.parse("2015-08-10"), "www.link.com", "http://img.us", 2);
        Job job2 = new Job(user, "job2", "desc", LocalDate.parse("2010-10-20"),
                LocalDate.parse("2015-08-10"), "www.link.com", "http://img.us", 3);

        List<Job> jobDataAsList = new ArrayList<Job>();
        jobDataAsList.add(job1);
        jobDataAsList.add(job2);

        given(jobService.findByUsernameOrderedByDate(user.getUsername())).willReturn(jobDataAsList);

        action = mvc.perform(get("/jobs").param("username", user.getUsername()).param("sortBy", "filtro"));     //se entiende que el filtro "filtro" no existe

    }

    @Then("The response status is {int}")
    public void theResponseStatusIs(int statusCode) throws Exception {
        action.andExpect(status().is(statusCode));
    }
}
