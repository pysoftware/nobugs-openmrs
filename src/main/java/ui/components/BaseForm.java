package ui.components;

import com.microsoft.playwright.Locator;

import java.util.List;

public class BaseForm {
    public final Locator locator;

    public BaseForm(Locator locator) {
        this.locator = locator;
    }

    public void click() {
        locator.click();
    }

    public List<Locator> all() {
        return locator.all();
    }

    public int count() {
        return locator.count();
    }
}
