package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage extends BasePage<HomePage> {
    protected final Locator SEARCH_BUTTON;
    protected final Locator SEARCH_INPUT;
    protected final Locator SEARCH_ENTER_BUTTON;


    public HomePage(Page page) {
        super(page);
        this.SEARCH_BUTTON = page.locator("[data-testid='searchPatientIcon']");
        this.SEARCH_INPUT = page.locator("input[placeholder='Search for a patient by name or identifier number']");
        this.SEARCH_ENTER_BUTTON = page.locator("button[type=submit] >> text=Search");

    }

    @Override
    protected String path() {
        return "/";
    }

    public PatientSearchPage searchPatientByIdOrName(String idOrName) {
        SEARCH_BUTTON.click();
        SEARCH_INPUT.fill(idOrName);
        page.waitForTimeout(300);
        SEARCH_ENTER_BUTTON.click();
        return new PatientSearchPage(page);
    }

}
