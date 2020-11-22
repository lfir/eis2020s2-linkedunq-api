package edu.unq.springboot.IntegrationTest.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.unq.springboot.models.User;
import edu.unq.springboot.service.UserService;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
public class AccountControllerTest {

	@Autowired
	private ObjectMapper mapper;
    @Autowired
    private UserService userService;
    private User user;
    private User user2;
    private String jsonUser;
    @Autowired
    private MockMvc mvc;
    private ResultActions action;

    @BeforeEach
    public void beforeEach() throws Exception {
        user = new User("Jose123","123456","Jose","Rodrigues","jose@gmial.com", true);
        user2 = new User("Jose124", "123456", "Jose", "Gonzales", "gonzales@gmail.com", false);
        jsonUser = mapper.writeValueAsString(user);
    }

    @Test
    public void RequestParaRegistrarUnNuevoUsuario() throws Exception {
        action = mvc.perform(post("/register")
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("Registered");
        action.andExpect(result);
    }

    @Test
    public void RegistroUnNuevoUsuarioPeroElUsuarioYaExiste() throws Exception {
        // Registro un usuario con Jose123 como usuario
        mvc.perform(post("/register")
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        // Registro otro usuario con el mismo usuario
        action = mvc.perform(post("/register")
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("Error");
        action.andExpect(result);
    }

    @Test
    public void RequestParaIniciarSesion() throws Exception {
        // Registro un usuario con Jose123 como usuario
        mvc.perform(post("/register")
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        // Inicio sesión
        action = mvc.perform(post("/login")
                .content(jsonUser).contentType(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("true");
        action.andExpect(result);
    }

    @Test 
    public void RequestParaIniciarSesionConDatosIncorrectos() throws Exception {
        action = mvc.perform(post("/login")
                .content(jsonUser).contentType(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("Error");
        action.andExpect(result);
    }  
    
    @Test
    public void RequestParaLink() throws Exception {
        // Registro un usuario con Jose123 como usuario
        mvc.perform(post("/register")
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        // Inicio sesión
        action = mvc.perform(post("/login")
                .content(jsonUser).contentType(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));
        action = mvc.perform(put("/link")
                .content(jsonUser).contentType(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("http://localhost:3000/repo/:Jose123");
        action.andExpect(result);
    }

    @Test
    public void RequestParaModificarElTitulo() throws Exception {
        User user = new User("nestor", "1234", "Nestor", "Ortigoza", "nestor@gmail.com");

        String requestUser = mapper.writeValueAsString(user);

        mvc.perform(post("/register")
                .content(requestUser)
                .contentType(MediaType.APPLICATION_JSON));

        user.modifyTitle("Nestor programador");
        requestUser = mapper.writeValueAsString(user);

        action = mvc.perform(put("/title")
                .content(requestUser)
                .contentType(MediaType.APPLICATION_JSON));


        ResultMatcher result = MockMvcResultMatchers.content().string("Nestor programador");
        action.andExpect(result);
    }

    @Test
    public void RequestParaModificarElTituloDeUnUsuarioQueNoExiste() throws Exception {
        User user = new User("nestor", "1234", "Nestor", "Ortigoza", "nestor@gmail.com");

        String requestUser = mapper.writeValueAsString(user);

        action = mvc.perform(put("/title")
                .content(requestUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("Username does not exist");
        action.andExpect(result);
    }

    @Test
    public void RequestParaObtenerElTitulo() throws Exception {
        User user = new User("nestor", "1234", "Nestor", "Ortigoza", "nestor@gmail.com");

        String requestUser = mapper.writeValueAsString(user);

        mvc.perform(post("/register")
                .content(requestUser)
                .contentType(MediaType.APPLICATION_JSON));

        action = mvc.perform(get("/title/" + user.getUsername()));

        ResultMatcher result = MockMvcResultMatchers.content().string("Mi Portfolio");
        action.andExpect(result);
    }

    @Test
    public void RequestParaObtenerElTituloDeUnUsuarioQueNoExiste() throws Exception {
        action = mvc.perform(get("/title/jose123"));

        ResultMatcher result = MockMvcResultMatchers.content().string("Username does not exist");
        action.andExpect(result);
    }
    
    @Test
    public void ConUnUsuarioNoRecruiterYUnoRecruiterAlPedirCandidatosSoloSeRecibeElNoRecruiter() throws Exception {
    	List<User> userDataAsList = new ArrayList<User>();
    	userDataAsList.add(this.user2);
    	this.userService.create(this.user);
    	this.userService.create(this.user2);
    	
    	MvcResult mvcResult = mvc.perform(get("/candidates"))
        		.andExpect(status().isOk())
        		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        		.andReturn();
    
    	String expectedResponseBody = mapper.writeValueAsString(userDataAsList);
    	String actualResponseBody = mvcResult.getResponse().getContentAsString();
    	assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @AfterEach
    public void afterEach() {
        userService.deleteAll();
    }
}
