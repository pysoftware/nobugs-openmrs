package ui.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

public class Button extends PageElement {
    public Button(Locator locator, String label) {
        super(locator.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName(label).setExact(true)));
    }

    public void click() {
        locator.click();
    }
}
