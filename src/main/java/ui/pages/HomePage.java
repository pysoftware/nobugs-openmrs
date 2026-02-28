package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage extends BasePage{
    protected final Locator BUTTON_SEARCH = page.locator("[data-testid='searchPatientIcon']");
    protected final Locator INPUT_SEARCH = page.locator("input[placeholder='Search for a patient by name or identifier number']");
    protected final Locator BUTTON_SEARCH_ENTER = page.locator("button[type=submit] >> text=Search");

    public HomePage(Page page) {
        super(page);
    }

    @Override
    protected String path() {
        return "/";
    }

    public PatientSearchPage searchPatientByIdOrName(String idOrName) {
        BUTTON_SEARCH.click();
        INPUT_SEARCH.fill(idOrName);
        page.waitForTimeout(300);
        BUTTON_SEARCH_ENTER.click();
        return new PatientSearchPage(page);
    }
}
