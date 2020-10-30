package edu.unq.springboot.cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/edu/unq/springboot/cucumber/resources/", plugin = { "json:target/cucumber.json", "pretty",
        "html:target/cucumber-reports" })
public class CucumberTest {

}
