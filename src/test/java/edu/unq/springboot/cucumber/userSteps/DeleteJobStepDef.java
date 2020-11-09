package edu.unq.springboot.cucumber.userSteps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unq.springboot.models.CreateJobRequestBody;
import edu.unq.springboot.models.Job;
import edu.unq.springboot.models.User;
import edu.unq.springboot.service.JobService;
import edu.unq.springboot.service.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
public class DeleteJobStepDef {

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

    @Given("A user's job")
    public void aUserJob() throws Exception {
        CreateJobRequestBody bd = new CreateJobRequestBody("Jose123", "titulo", "desc", "2010-01-01", "2010-01-01", "https://www.mercadolibre.com.ar/");
        String json = this.mapper.writeValueAsString(bd);
        action = mvc.perform(post("/jobs/create").content(json).contentType(MediaType.APPLICATION_JSON));

    }

    @When("A user request to delete a job")
    public void aUserRequestToDeleteAJob() throws Exception {
        action = action.andDo(mvcResult -> mvc.perform(delete("/job/1")));
    }

    @Then("The response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) throws Exception {
        action.andExpect(status().is(statusCode));
    }
}
