package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PatientRegistrationPage extends BasePage {
    private final Locator BUTTON_SEARCH = page.locator("[data-testid='searchPatientIcon']");
    private final Locator INPUT_SEARCH = page.locator("input[placeholder='Search for a patient by name or identifier number']");
    private final Locator BUTTON_SEARCH_ENTER = page.locator("button[type=submit] >> text=Search");

    public PatientRegistrationPage(Page page) {
        super(page);
    }

    @Override
    protected String path() {
        return "patient-registration";
    }

    public PatientSearchPage searchPatientByIdOrName(String idOrName) {
        BUTTON_SEARCH.click();
        INPUT_SEARCH.fill(idOrName);
        page.waitForTimeout(300);
        BUTTON_SEARCH_ENTER.click();
        return new PatientSearchPage(page);
    }
}
