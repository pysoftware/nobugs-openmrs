package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.Objects;

public class PatientSummaryPage extends HomePage {

    private final Locator TEXT_ID = page.locator("label[class*='label'][for*='identifier'] span:last-child");
    private final Locator TEXT_NAME = page.locator(".sb-avatar__text");


    protected PatientSummaryPage(Page page, String patientUuid) {
        super(page);
        this.patientUuid = Objects.requireNonNull(patientUuid, "patientUuid is required");
    }


    @Override
    protected String path() {
        return String.format("patient/%s/chart/Patient%%20Summary", patientUuid);
    }

    public String getOpenMrsId() {
        return TEXT_ID
                .first()
                .textContent()
                .trim();
    }

    public String getPatientName() {
        return TEXT_NAME.getAttribute("title");
    }


}
