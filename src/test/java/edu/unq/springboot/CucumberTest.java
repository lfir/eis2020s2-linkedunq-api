package edu.unq.springboot;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/edu/unq/springboot/resources/", plugin = { "json:target/cucumber.json", "pretty",
        "html:target/cucumber-reports" })
public class CucumberTest {

}