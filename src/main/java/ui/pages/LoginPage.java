package ui.pages;

import api.configs.Config;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;


public class LoginPage extends BasePage<LoginPage> {
    private final Locator USERNAME_INPUT;
    private final Locator PASSWORD_INPUT;
    private final Locator CONTINUE_BUTTON;
    private final Locator SUBMIT_BUTTON;
    private final Locator WELCOME_ADMIN_TEXT;

    public LoginPage(Page page) {
        super(page);
        this.USERNAME_INPUT = page.locator("input[id='username']");
        this.PASSWORD_INPUT = page.locator("input[id='password']");
        this.CONTINUE_BUTTON = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                .setName("Continue"));
        this.SUBMIT_BUTTON = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log in"));
        this.WELCOME_ADMIN_TEXT = page.getByText("Welcome admin");
    }

    @Override
    public String path() {
        return "login";
    }

    public LocationPage loginAsAdmin() {
        open();
        USERNAME_INPUT.fill(Config.getProperty("admin.username"));
        CONTINUE_BUTTON.click();
        PASSWORD_INPUT.fill(Config.getProperty("admin.password"));
        SUBMIT_BUTTON.click();
        WELCOME_ADMIN_TEXT.isVisible();

        return new LocationPage(page);
    }

}

