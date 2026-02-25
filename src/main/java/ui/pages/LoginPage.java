package ui.pages;

import api.configs.Config;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;


public class LoginPage extends BasePage {
    private final Locator INPUT_USERNAME = page.locator("input[id='username']");
    private final Locator INPUT_PASSWORD = page.locator("input[id='password']");
    private final Locator BUTTON_CONTINUE = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
            .setName("Continue"));
    private final Locator BUTTON_SUBMIT = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log in"));
    private final Locator WELCOME_ADMIN_TEXT = page.getByText("Welcome admin");

    public LoginPage(Page page) {
        super(page);
    }

    @Override
    public String getRelativePath() {
        return "/openmrs/spa/login";
    }

    public LocationPage loginAsAdmin() {
        open();
        INPUT_USERNAME.fill(Config.getProperty("admin.username"));
        BUTTON_CONTINUE.click();
        INPUT_PASSWORD.fill(Config.getProperty("admin.password"));
        BUTTON_SUBMIT.click();
        WELCOME_ADMIN_TEXT.isVisible();

        return new LocationPage(page);
    }

}
