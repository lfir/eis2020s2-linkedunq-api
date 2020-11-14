package edu.unq.springboot.IntegrationTest.services;

import edu.unq.springboot.models.Job;
import edu.unq.springboot.service.UserService;
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

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    public void beforeEach() {
        User usuario = new User();
        usuario.setEmail("email");
        usuario.setFirstName("fname");
        usuario.setLastName("lname");
        usuario.setPassword("pass");
        usuario.setUsername("nick");
        userService.create(usuario);
        User usuarioDos = new User("DosSantos", "pass", "fname", "lname", "correo");
        userService.create(usuarioDos);
    }

    @Test
    public void traigoUnUsuarioDesdeLaBasePorSuUsername() {
        User usuario = userService.findByUsername("nick");
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
        User usuario = userService.findByUsername("nick");
        Job trabajo = new Job(usuario, "Titulo", "Descripcion", LocalDate.parse("2010-10-20"),
        		LocalDate.parse("2015-08-10"), "www.link.com", "http://img.us");

        usuario = userService.addJob(trabajo, usuario);
        Assert.assertEquals(1, usuario.getJobs().size());
        trabajo = usuario.getJobs().get(0);
        Assert.assertNotNull(trabajo.getId());
        Assert.assertEquals(usuario, trabajo.getOwner());
        Assert.assertEquals("Titulo", trabajo.getTitulo());
        Assert.assertEquals("Descripcion", trabajo.getDescripcion());
        Assert.assertEquals("2010-10-20", trabajo.getFechaInicioTrabajo().toString());
        Assert.assertEquals("2015-08-10", trabajo.getFechaFinTrabajo().toString());
        Assert.assertEquals("www.link.com", trabajo.getEnlace());
    }

    @AfterEach
    public void afterEach() {
        userService.deleteAll();
    }

}
