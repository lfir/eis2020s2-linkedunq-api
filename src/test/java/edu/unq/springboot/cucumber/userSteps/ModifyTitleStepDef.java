package edu.unq.springboot.cucumber.userSteps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unq.springboot.models.User;
import edu.unq.springboot.repository.JobRepository;
import edu.unq.springboot.repository.UserRepository;
import edu.unq.springboot.service.JobService;
import edu.unq.springboot.service.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
@RunWith(SpringRunner.class)
public class ModifyTitleStepDef {
    @Autowired
    private MockMvc mvc;
    @Autowired @MockBean
    private UserService userService;
    private ResultActions action;
    private String jsonObject;
    @Autowired
    private ObjectMapper mapper;
    private User user;
    private String username;
    @Given("A user with the default title")
    public void userWithDefaultTitle () throws Exception {
        user = new User("jose","1234","Jose","Rodrigues","joserodrigues@gmail.com");
    }

    @When("User wants to change title")
    public void modifyDefaultTitle () throws Exception {
        user.modifyTitle("Jose desarrollador");
        jsonObject = mapper.writeValueAsString(user);
        given(userService.findByUsername(user.getUsername())).willReturn(user);
        action = mvc.perform(put("/title").content(jsonObject).contentType(MediaType.APPLICATION_JSON));
    }

    @Then("The title is modified")
    public void theTitleIsModified () throws  Exception {
        ResultMatcher result = MockMvcResultMatchers.content().string("Jose desarrollador");
        action.andExpect(result);
    }

    @Given("A username")
    public void userName () {
        user = new User("jose","1234","Jose","Rodrigues","joserodrigues@gmail.com");
        username = "jose";
    }

    @When("you want to get the title")
    public void getTheTitle () throws Exception {
        given(userService.findByUsername(user.getUsername())).willReturn(user);
        action = mvc.perform(get("/title/" + username));
    }

    @Then("The title is obtained")
    public void TheTitleIsObtained () throws Exception {
        ResultMatcher result = MockMvcResultMatchers.content().string("Mi Portfolio");
        action.andExpect(result);
    }
}
