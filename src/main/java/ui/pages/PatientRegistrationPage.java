package ui.pages;

import com.microsoft.playwright.Page;

public class PatientRegistrationPage extends BasePage{
    public PatientRegistrationPage(Page page) {
        super(page);
    }

    @Override
    protected String path() {
        return "patient-registration";
    }
}
