package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PatientSummaryPage extends BasePage {
    private final Locator TEXT_ID = page.locator("label[class*='label'][for*='identifier'] span:last-child");
    private final Locator TEXT_NAME = page.locator(".sb-avatar__text");


    protected PatientSummaryPage(Page page) {
        super(page);
    }

    @Override
    protected String path() {
        return "";
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
