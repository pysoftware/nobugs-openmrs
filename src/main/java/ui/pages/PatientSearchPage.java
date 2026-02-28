package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PatientSearchPage extends BasePage {
    private final Locator ROW_PATIENT = page.locator("a[href*='/patient/'][href*='/chart/']")
            .first();
    private final Locator NO_CHARTS_MESSAGE = page.getByText("Sorry, no patient charts were found");

    protected PatientSearchPage(Page page) {
        super(page);
    }

    @Override
    protected String path() {
        return "";
    }

    public PatientSummaryPage searchPatient() {
        ROW_PATIENT.click();
        return new PatientSummaryPage(page, patientUuid);
    }

    public boolean patientNotFound() {
        return NO_CHARTS_MESSAGE.isVisible();
    }
}

