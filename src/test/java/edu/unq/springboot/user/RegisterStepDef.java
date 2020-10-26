package edu.unq.springboot.user;


import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.unq.springboot.controllers.AccountController;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@CucumberContextConfiguration
@WebMvcTest(AccountController.class)
public class RegisterStepDef {
    @Autowired
    private MockMvc mvc;

    ResultActions action;

    @When("Request to register a new user")
    public void request_to_register_a_new_user() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        MockUser user = new MockUser("Jose", "Sand");
        String json = mapper.writeValueAsString(user);

    }

    @Then("Response status code of 200")
    public void response_ok() throws Exception {
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}