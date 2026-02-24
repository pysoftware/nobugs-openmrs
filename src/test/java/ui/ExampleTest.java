package ui;

import api.configs.Config;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ExampleTest extends BaseUiTest{
    @Test
    void loginAndOpenServiceQueues() {
        com.microsoft.playwright.Page currentPage = page;
        currentPage.navigate("/openmrs/spa/login");  // ← правильный вызов

        // или полный URL, если baseURL не задан:
        // page.goto("http://localhost/openmrs/spa/login");

        currentPage.fill("input[id='username']", Config.getProperty("admin.username"));
        currentPage.getByText("Continue").click();
        currentPage.fill("input[id='password']", Config.getProperty("admin.password"));
        currentPage.click("button[type='submit']");

        // Ждём редиректа после логина (можно уточнить паттерн)
        currentPage.waitForURL(url -> url.toString().contains("/home"));

        // Переход на service queues
        currentPage.navigate("/openmrs/spa/home/service-queues");

        // Дальше твои проверки...
        // Например:
        currentPage.waitForSelector("text=Service Queues", new Page.WaitForSelectorOptions().setTimeout(15000));
        // или
        assertTrue(page.isVisible("text=Add patient to queue"));
    }

}
