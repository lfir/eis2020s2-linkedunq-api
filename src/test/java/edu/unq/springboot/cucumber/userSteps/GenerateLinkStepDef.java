package edu.unq.springboot.cucumber.userSteps;

import edu.unq.springboot.models.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class GenerateLinkStepDef {
    User usuario;
    String link;

    @Given("A user without his portfolio link")
    public void user_have_not_link_that_repository() {
         usuario = new User("nelson","1234","Nelson","Gonzalez","nelgonzalez88@gmail.com");
    }

    @When("The user request to generate a link to they portfolio")
    public void user_requests_to_generate_link() {
      usuario.generateLink();
    }

    @Then("A link should be generated")
    public void a_link_is_generated() {
        link = usuario.getLink();
        Assert.assertNotNull(link);
    }



    @Then("The user should not have an associated portfolio link")
    public void user_cant_generate_link() {
        Assert.assertNull(link);
    }


    @When("An user have not created a link to his portfolio")
    public void user_without_portfolio_link() {
        link = usuario.getLink();
    }
}
