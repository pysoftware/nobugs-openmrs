package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class PatientRegistrationPage extends BasePage{
    private final Locator BUTTON_SEARCH = page.locator("[data-testid='searchPatientIcon']");
    private final Locator INPUT_SEARCH = page.locator("input[placeholder='Search for a patient by name or identifier number']");
    private final Locator BUTTON_SEARCH_ENTER = page.locator("[type='submit']");
    public PatientRegistrationPage(Page page) {
        super(page);
    }

    @Override
    protected String path() {
        return "patient-registration";
    }

    public PatientSummaryPage searchPatientById(String idOrName){
        BUTTON_SEARCH.click();
        INPUT_SEARCH.fill(idOrName);
        BUTTON_SEARCH_ENTER.click();

        return new PatientSummaryPage(page);
    }
}
