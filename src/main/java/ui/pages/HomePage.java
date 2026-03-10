package ui.pages;

import com.microsoft.playwright.Page;

public class HomePage extends BasePage<HomePage> {
    public HomePage(Page page) {
        super(page);
    }

    @Override
    protected String path() {
        return "home";
    }
}
