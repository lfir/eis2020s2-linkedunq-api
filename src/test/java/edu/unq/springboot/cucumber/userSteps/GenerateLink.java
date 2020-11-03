package edu.unq.springboot.cucumber.userSteps;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unq.springboot.models.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

public class GenerateLink {
    User usuario;
    String link;

    @Given("User have not link that repository")
    public void user_have_not_link_that_repository() {
         usuario = new User("nelson","1234","Nelson","Gonzalez","nelgonzalez88@gmail.com");
    }



    @When("User requests to generate link")
    public void user_requests_to_generate_link() {
      usuario.generateLink();
    }
    @Then("a link is generated")
    public void a_link_is_generated() {
        usuario.getLink();
    }





    @Then("User cant generate link")
    public void user_cant_generate_link() {
        Assert.assertEquals(usuario.getLink(),null);
    }


}
