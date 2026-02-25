package ui.pages;

import com.microsoft.playwright.Page;

public class LocationPage extends BasePage {
    protected LocationPage(Page page) {
        super(page);
    }

    @Override
    protected String getRelativePath() {
        return "openmrs/spa/login/location";
    }
}
