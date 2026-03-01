package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ui.elements.PatientBanner;

public class PatientSearchPage extends HomePage {
    private final Locator NO_CHARTS_MESSAGE;
    private final PatientBanner patientBanner;

    protected PatientSearchPage(Page page) {
        super(page);
        Locator bannerRoot = page.locator("div[role='banner']");
        this.NO_CHARTS_MESSAGE = page.getByText("Sorry, no patient charts were found");
        this.patientBanner = new PatientBanner(bannerRoot);
    }

    @Override
    protected String path() {
        return "";
    }

    public PatientSummaryPage searchPatient() {
        patientBanner.clickPatientName();
        return new PatientSummaryPage(page, patientUuid);
    }

    public PatientSearchPage openActions() {
        patientBanner.openActions();
        return this;
    }

    public PatientSearchPage showMore() {
        patientBanner.openActions();
        return this;
    }

    public boolean patientNotFound() {
        return NO_CHARTS_MESSAGE.isVisible();
    }
}

