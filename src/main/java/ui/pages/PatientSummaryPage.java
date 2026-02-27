package ui.pages;

import com.microsoft.playwright.Page;

public class PatientSummaryPage extends BasePage{
    protected PatientSummaryPage(Page page) {
        super(page);
    }

    @Override
    protected String path() {
        return "";
    }
}
