package edu.unq.springboot.IntegrationTest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.unq.springboot.integrationTest.controllers.AccountController;
import edu.unq.springboot.integrationTest.controllers.service.UserService;
import edu.unq.springboot.models.User;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc

public class AccountControllerTest {

    @Autowired
    private UserService userService;
    private User user;
    private String json;

    @Autowired
    private MockMvc mvc;
    ResultActions action;

    @BeforeEach
    public void beforeEach() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        user = new User("Jose123","123456","Jose","Rodrigues","jose@gmial.com");
        json = mapper.writeValueAsString(user);

    }

    @Test
    public void RequestParaRegistrarUnNuevoUsuario () throws Exception {
        action = mvc.perform(post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("Registered");
        action.andExpect(result);
    }


    @AfterEach
    public void afterEach() {
        userService.deleteAll();
    }

}