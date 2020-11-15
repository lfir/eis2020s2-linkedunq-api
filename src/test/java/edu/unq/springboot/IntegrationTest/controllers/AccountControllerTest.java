package edu.unq.springboot.IntegrationTest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.unq.springboot.service.UserService;
import edu.unq.springboot.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc

public class AccountControllerTest {

    @Autowired
    private UserService userService;
    private User user;
    private User user2;
    private String jsonUser;
    private String jsonUser2;

    @Autowired
    private MockMvc mvc;
    ResultActions action;

    @BeforeEach
    public void beforeEach() throws Exception {
        user = new User("Jose123","123456","Jose","Rodrigues","jose@gmial.com");
        user2 = new User("Jose123", "123456", "Jose", "Gonzales", "gonzales@gmail.com");
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        jsonUser = mapper.writeValueAsString(user);
        jsonUser2 = mapper.writeValueAsString(user2);

    }

    @Test
    public void RequestParaRegistrarUnNuevoUsuario () throws Exception {
        action = mvc.perform(post("/register")
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("Registered");
        action.andExpect(result);
    }

    @Test
    public void RegistroUnNuevoUsuarioPeroElUsuarioYaExiste () throws Exception {
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
    public void RequestParaIniciarSesion () throws  Exception {
        // Registro un usuario con Jose123 como usuario
        mvc.perform(post("/register")
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        // Inicio sesión
        action = mvc.perform(post("/login")
                .content(jsonUser).contentType(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("OK");
        action.andExpect(result);
    }

    @Test void RequestParaIniciarSesionConDatosIncorrectos () throws  Exception {
        action = mvc.perform(post("/login")
                .content(jsonUser).contentType(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("Error");
        action.andExpect(result);
    }  @Test
    public void RequestParaLink () throws  Exception {
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
    public void RequestParaModificarElTitulo () throws  Exception {
        User user = new User("nestor", "1234", "Nestor", "Ortigoza", "nestor@gmail.com");

        ObjectMapper mapper = new ObjectMapper();
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
    public void RequestParaModificarElTituloDeUnUsuarioQueNoExiste () throws Exception {
        User user = new User("nestor", "1234", "Nestor", "Ortigoza", "nestor@gmail.com");

        ObjectMapper mapper = new ObjectMapper();
        String requestUser = mapper.writeValueAsString(user);

        action = mvc.perform(put("/title")
                .content(requestUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("Username does not exist");
        action.andExpect(result);
    }

    @Test
    public void RequestParaObtenerElTitulo () throws Exception {
        User user = new User("nestor", "1234", "Nestor", "Ortigoza", "nestor@gmail.com");

        ObjectMapper mapper = new ObjectMapper();
        String requestUser = mapper.writeValueAsString(user);

        mvc.perform(post("/register")
                .content(requestUser)
                .contentType(MediaType.APPLICATION_JSON));

        action = mvc.perform(get("/title/" + user.getUsername()));

        ResultMatcher result = MockMvcResultMatchers.content().string("Nestor Ortigoza");
        action.andExpect(result);
    }

    @Test
    public void RequestParaObtenerElTituloDeUnUsuarioQueNoExiste () throws Exception {


        action = mvc.perform(get("/title/jose123"));


        ResultMatcher result = MockMvcResultMatchers.content().string("Username does not exist");
        action.andExpect(result);
    }

    @AfterEach
    public void afterEach() {
        userService.deleteAll();
    }

}