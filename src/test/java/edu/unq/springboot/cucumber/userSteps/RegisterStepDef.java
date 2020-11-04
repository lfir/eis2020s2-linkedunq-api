package edu.unq.springboot.cucumber.userSteps;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.unq.springboot.models.User;
import edu.unq.springboot.service.UserService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest
public class RegisterStepDef {
    @Autowired
    private MockMvc mvc;

    ResultActions action;

    @Autowired
    UserService userService;

    @MockBean
    UserService userservice;

    private User user;

    @Given("A user with valid data")
    public void user_with_valid_credentials() {
        this.user = new User("Jose123","123","Jose","Rodrigues","jose@gmial.com");
    }

    @When("The user requests to register on the site")
    public void request_to_register_a_new_user() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        String json = mapper.writeValueAsString(user);
        action = mvc.perform(post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

    }

    @When("The user login on the site")
    public void request_to_login_as_user() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        String jsonUser = mapper.writeValueAsString(user);
        given(userService.validateUser(user.getUsername(), user.getPassword())).willReturn(true);
        // Inicio sesión
        action = mvc.perform(post("/login")
                .content(jsonUser).contentType(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("OK");
        action.andExpect(result);

    }


    @Given("A registered user on the site")
    public void register_an_user_on_site() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        String json = mapper.writeValueAsString(new User("Jose123","123","Jose","Rodrigues","jose@gmial.com"));
        mvc.perform(post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));
        action = mvc.perform(post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

    }

    @When("The user requests to generate a portfolio's link")
    public void theUserRequestsToGenerateAPortfolioSLink() throws Exception {
        action = action.andDo(mvcResult -> mvc.perform(put("/link")));
    }

    @Then("The response status should be {string}")
    public void theResponseStatusShouldBe(String statusCode) throws Exception {
        action.andExpect(status().is(Integer.parseInt(statusCode)));
    }
}