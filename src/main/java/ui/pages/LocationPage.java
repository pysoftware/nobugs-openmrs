package ui.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LocationPage extends BasePage<LocationPage> {
    public LocationPage(Page page) {
        super(page);
    }

    @Override
    protected String path() {
        return "login/location";
    }

    public void selectFirstLocationAndConfirm() {
        this.open();
        page.locator("label.cds--radio-button__label").first().click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm")).click();
    }
}
