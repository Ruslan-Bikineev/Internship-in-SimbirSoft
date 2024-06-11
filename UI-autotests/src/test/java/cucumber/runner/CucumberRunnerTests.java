package cucumber.runner;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags = "",
        features = "src/test/resources/features/LoginPage.feature",
        glue = "cucumber.definitions",
        plugin = {})
public class CucumberRunnerTests extends AbstractTestNGCucumberTests {
}
