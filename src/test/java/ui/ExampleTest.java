package ui;

import api.configs.Config;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ExampleTest extends BaseUiTest{
    @Test
    void loginAndOpenServiceQueues() {
        com.microsoft.playwright.Page currentPage = page;
        currentPage.navigate("/openmrs/spa/login");

        currentPage.fill("input[id='username']", Config.getProperty("admin.username"));
        currentPage.getByText("Continue").click();
        currentPage.fill("input[id='password']", Config.getProperty("admin.password"));
        currentPage.click("button[type='submit']");

        currentPage.waitForURL(url -> url.toString().contains("/home"));

        currentPage.navigate("/openmrs/spa/home/service-queues");

        currentPage.waitForSelector("text=Service Queues", new Page.WaitForSelectorOptions().setTimeout(15000));
        assertTrue(page.isVisible("text=Patients currently in queue"));
    }

}
