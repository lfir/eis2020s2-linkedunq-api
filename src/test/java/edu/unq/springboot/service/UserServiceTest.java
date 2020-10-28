package edu.unq.springboot.service;

import edu.unq.springboot.models.Job;
import edu.unq.springboot.models.User;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;
    private User usuario;

    @BeforeEach
    public void beforeEach() {
        usuario = new User();
        usuario.setEmail("email");
        usuario.setFirstName("fname");
        usuario.setLastName("lname");
        usuario.setPassword("pass");
        usuario.setUsername("nick");
        userService.create(usuario);
        User usuarioDos = new User("DosSantos", "pass", "fname", "lname", "email");
        userService.create(usuarioDos);
    }

    @Test
    public void traigoUnUsuarioDesdeLaBasePorSuUsername() {
        usuario = userService.findByUsername("nick");
        Assert.assertNotNull(usuario.getId());
        Assert.assertEquals("nick", usuario.getUsername());
        Assert.assertEquals("pass", usuario.getPassword());
        Assert.assertEquals("fname", usuario.getFirstName());
        Assert.assertEquals("lname", usuario.getLastName());
        Assert.assertEquals("email", usuario.getEmail());
        Assert.assertEquals(0 , usuario.getJobs().size());
    }

    @Test
    public void validoUnLogInExitoso() {
        Assert.assertTrue(userService.validateUser("nick", "pass"));
    }

    @Test
    public void validoUnLogInConContraseniaIncorrecta() {
        Assert.assertFalse(userService.validateUser("nick", ""));
    }

    @Test
    public void validoUnLogInConUnUsuarioQueNoExiste() {
        Assert.assertFalse(userService.validateUser("", "pass"));
    }

    @Test
    public void agregoUnTrabajoAUnUsuario() {
        usuario = userService.findByUsername("nick");
        Job trabajo = new Job(usuario, "Titulo", "Descripcion", LocalDate.parse("2010-10-20"), LocalDate.parse("2015-08-10"));
        usuario.addJob(trabajo);
        User usuarioNuevo = userService.update(usuario);
        Assert.assertEquals(1, usuarioNuevo.getJobs().size());
    }

    @AfterEach
    public void afterEach() {
        userService.deleteAll();
    }

}
