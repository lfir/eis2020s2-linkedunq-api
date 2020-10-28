package edu.unq.springboot.UserSteps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
public class CreateJobStepDef {
    @Autowired
    private MockMvc mvc;

    ResultActions action;

    @When("Request to create a new job")
    public void request_to_create_a_new_job() throws Exception {
        action = mvc.perform(post("/jobs/create").contentType(MediaType.APPLICATION_JSON));
    }
    
    @Then("The new job is created and saved")
    public void response_ok() throws Exception {
        action.andExpect(status().isOk());
        action.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

}
