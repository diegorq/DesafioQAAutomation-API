import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty",
		"html:target/cucumber.html" }, monochrome = false, features = "features", tags = "@api", glue = "classpath:stepDefinitions")
public class Runner {



}
