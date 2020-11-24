package edu.unq.springboot.cucumber.userSteps;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import edu.unq.springboot.models.User;
import edu.unq.springboot.service.JobService;
import edu.unq.springboot.service.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@WebMvcTest
@RunWith(SpringRunner.class)
public class GetCandidates {
    @Autowired
    private MockMvc mvc;
    @Autowired @MockBean
    private UserService userService;
    @Autowired @MockBean
    private JobService jobService;
    private ResultActions action;
    private User recruiter;
    private User notRecruiter;
    
    @Given("A recruiter and a non-recruiter user registered")
    public void createRecruiterAndNonRecruiterUsers() {
    	this.recruiter = new User("DosSantos", "pass", "fname", "lname", "correo", true);
    	this.notRecruiter = new User("nick", "pass", "fname", "lname", "email", false);
    	this.userService.create(this.recruiter);
    	this.userService.create(this.notRecruiter);
    }
    
    @When("I request the list of candidates")
    public void request_to_get_jobs_of_user0() throws Exception {
    	List<User> userDataAsList = new ArrayList<User>();
    	userDataAsList.add(this.notRecruiter);
    	given(userService.getNonRecruiters()).willReturn(userDataAsList);

    	action = mvc.perform(get("/candidates"));
    }
    
    @Then("The list with the non-recruiter user only is returned")
    public void get_jobs_response_ok() throws Exception {
        action.andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string(containsString(this.notRecruiter.getUsername())))
            .andExpect(content().string(not(containsString(this.recruiter.getUsername()))));
    }
}
