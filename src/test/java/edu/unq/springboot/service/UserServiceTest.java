package edu.unq.springboot.service;

import edu.unq.springboot.LinkedUNQApplication;
import edu.unq.springboot.models.User;
import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LinkedUNQApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    private User usuario;

    @BeforeEach
    public void beforeEach() {
        usuario = new User("nick", "pass", "fname", "lname", "email");
        userService.create(usuario);
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

    @AfterEach
    public void afterEach() {
        userService.deleteAll();
    }

}