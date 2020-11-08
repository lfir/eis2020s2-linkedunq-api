package edu.unq.springboot.IntegrationTest.services;

import edu.unq.springboot.models.Job;
import edu.unq.springboot.models.User;
import edu.unq.springboot.service.JobService;
import edu.unq.springboot.service.UserService;

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
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class JobServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private JobService jobService;

    @BeforeEach
    public void beforeEach() {
        User usuario = new User("Ricardo", "password", "firstname", "lastname", "ricardo@gmail.com");
        usuario = userService.create(usuario);
        Job trabajo = new Job(usuario, "Titulo", "Descripcion", LocalDate.parse("2010-10-20"), LocalDate.parse("2015-08-10"), "https://www.mercadolibre.com.ar");
        userService.addJob(trabajo, usuario);
    }

    @Test
    public void obtengoUnTrabajoDeUnUsuarioYLoActualizo() {
        User usuario = userService.findByUsername("Ricardo");
        Job trabajo = usuario.getJobs().get(0);

        trabajo.setTitulo("Nuevo titulo");
        trabajo.setDescripcion("Nueva descripcion");
        trabajo.setFechaInicioTrabajo(LocalDate.parse("2012-12-22"));
        trabajo.setFechaFinTrabajo(LocalDate.parse("2012-12-22"));
        trabajo.setEnlace("https://santander.com.ar/");
        jobService.update(trabajo);

        usuario = userService.findByUsername("Ricardo");
        trabajo = usuario.getJobs().get(0);
        Assert.assertNotNull(trabajo.getId());
        Assert.assertEquals("Nuevo titulo", trabajo.getTitulo());
        Assert.assertEquals("Nueva descripcion", trabajo.getDescripcion());
        Assert.assertEquals("2012-12-22", trabajo.getFechaInicioTrabajo().toString());
        Assert.assertEquals("2012-12-22", trabajo.getFechaFinTrabajo().toString());
        Assert.assertEquals("https://santander.com.ar/", trabajo.getEnlace());
    }
    
    @Test
    public void obtengoUnTrabajoPorIdYUsuarioYLoActualizo() {
        User usuario = userService.findByUsername("Ricardo");
        Job trabajo = usuario.getJobs().get(0);

        trabajo.setTitulo("Nuevo titulo");
        trabajo.setDescripcion("Nueva descripcion");
        trabajo.setFechaInicioTrabajo(LocalDate.parse("2012-12-22"));
        trabajo.setFechaFinTrabajo(LocalDate.parse("2012-12-22"));
        trabajo.setEnlace("https://santander.com.ar/");
        jobService.update(usuario.getUsername(), trabajo.getId(), trabajo);

        usuario = userService.findByUsername("Ricardo");
        trabajo = usuario.getJobs().get(0);
        Assert.assertNotNull(trabajo.getId());
        Assert.assertEquals("Nuevo titulo", trabajo.getTitulo());
        Assert.assertEquals("Nueva descripcion", trabajo.getDescripcion());
        Assert.assertEquals("2012-12-22", trabajo.getFechaInicioTrabajo().toString());
        Assert.assertEquals("2012-12-22", trabajo.getFechaFinTrabajo().toString());
        Assert.assertEquals("https://santander.com.ar/", trabajo.getEnlace());
    }
   
    @Test
    public void actualizoUnTrabajoConOtroIdElOriginalNoSeModifica() {
        User usuario = userService.findByUsername("Ricardo");
        Job trabajo = usuario.getJobs().get(0);

        trabajo.setTitulo("Nuevo titulo");
        trabajo.setDescripcion("Nueva descripcion");
        trabajo.setFechaInicioTrabajo(LocalDate.parse("2012-12-22"));
        trabajo.setFechaFinTrabajo(LocalDate.parse("2012-12-22"));
        trabajo.setEnlace("https://santander.com.ar/");
        jobService.update(usuario.getUsername(), trabajo.getId() + 1, trabajo);

        usuario = userService.findByUsername("Ricardo");
        trabajo = usuario.getJobs().get(0);
        Assert.assertNotEquals("Nuevo titulo", trabajo.getTitulo());
        Assert.assertNotEquals("Nueva descripcion", trabajo.getDescripcion());
        Assert.assertNotEquals("2012-12-22", trabajo.getFechaInicioTrabajo().toString());
        Assert.assertNotEquals("2012-12-22", trabajo.getFechaFinTrabajo().toString());
        Assert.assertNotEquals("https://santander.com.ar/", trabajo.getEnlace());
    }

    @Test
    public void obtengoTodosLosTrabajosDeUnUsuarioPorSuUsername() {
        User usuario1 = new User("Hernan", "password", "firstname", "lastname", "hernan@gmail.com");
        usuario1 = userService.create(usuario1);
        Job trabajo1 = new Job(usuario1, "Titulo1", "Descripcion", LocalDate.parse("2010-10-20"), LocalDate.parse("2015-08-10"), "www.link.com");
        userService.addJob(trabajo1, usuario1);

        User usuario2 = new User("Pedro", "password", "firstname", "lastname", "pedro@gmail.com");
        usuario2 = userService.create(usuario2);
        Job trabajo2 = new Job(usuario2, "Titulo2", "Descripcion", LocalDate.parse("2010-10-20"), LocalDate.parse("2015-08-10"), "www.link.com");
        Job trabajo3 = new Job(usuario2, "Titulo3", "Descripcion", LocalDate.parse("2010-10-20"), LocalDate.parse("2015-08-10"), "www.link.com");
        usuario2 = userService.addJob(trabajo2, usuario2);
        userService.addJob(trabajo3, usuario2);

        User usuario3 = userService.findByUsername("Ricardo");
        Job trabajo4 = new Job(usuario3, "Titulo2", "Descripcion", LocalDate.parse("2010-10-20"), LocalDate.parse("2015-08-10"), "www.link.com");
        Job trabajo5 = new Job(usuario3, "Titulo3", "Descripcion", LocalDate.parse("2010-10-20"), LocalDate.parse("2015-08-10"), "www.link.com");
        Job trabajo6 = new Job(usuario3, "Titulo2", "Descripcion", LocalDate.parse("2010-10-20"), LocalDate.parse("2015-08-10"), "www.link.com");
        usuario3 = userService.addJob(trabajo4, usuario3);
        usuario3 = userService.addJob(trabajo5, usuario3);
        userService.addJob(trabajo6, usuario3);

        List<Job> trabajos1 = jobService.findByUsername("Hernan");
        Assert.assertEquals(1, trabajos1.size());

        List<Job> trabajos2 = jobService.findByUsername("Pedro");
        Assert.assertEquals(2, trabajos2.size());

        List<Job> trabajos3 = jobService.findByUsername("Ricardo");
        Assert.assertEquals(4, trabajos3.size());
    }

    @AfterEach
    public void afterEach() {
        userService.deleteAll();
    }

}
