package ui.pages;

import api.configs.Config;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;


public class LoginPage extends BasePage{
    private final Locator INPUT_USERNAME = page.locator("input[id='username']");
    private final Locator INPUT_PASSWORD = page.locator("input[id='password']");
    private final Locator BUTTON_CONTINUE = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
            .setName("Continue"));
    private final Locator BUTTON_SUBMIT = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log in"));

    protected LoginPage(Page page) {
        super(page);
    }

    @Override
    protected String getRelativePath() {
        return "/openmrs/spa/login";
    }

    public void loginAsAdmin() {
        open();
        INPUT_USERNAME.fill(Config.getProperty("admin.username"));
        BUTTON_CONTINUE.click();
        INPUT_PASSWORD.fill(Config.getProperty("admin.password"));
        BUTTON_SUBMIT.click();

    }
}
