package edu.unq.springboot.cucumber.userSteps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.unq.springboot.IntegrationTest.models.CreateJobRequestBody;
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
    @MockBean
    private UserService userService;
    @MockBean
    private JobService jobService;
    private ResultActions action;

    @When("Request to create a new job")
    public void request_to_create_a_new_job() throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        CreateJobRequestBody bd = new CreateJobRequestBody("Jose123", "titulo", "desc", "2010-01-01", "2010-01-01");
        String json = mapper.writeValueAsString(bd);
        action = mvc.perform(post("/jobs/create").content(json).contentType(MediaType.APPLICATION_JSON));
    }
    
    @Then("The new job is created and saved")
    public void response_ok() throws Exception {
        action.andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

}
