package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PatientSearchPage extends HomePage {
    private final Locator ROW_PATIENT;
    private final Locator NO_CHARTS_MESSAGE;

    protected PatientSearchPage(Page page) {
        super(page);
        this.ROW_PATIENT = page.locator("a[href*='/patient/'][href*='/chart/']").first();
        this.NO_CHARTS_MESSAGE = page.getByText("Sorry, no patient charts were found");
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

