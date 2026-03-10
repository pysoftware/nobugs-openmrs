package ui.elements;

import com.microsoft.playwright.Locator;

public class PageElement {

    protected final Locator locator;

    public PageElement(Locator locator) {
        this.locator = locator;
    }

    public String getAttribute(String name) {
        return locator.getAttribute(name);
    }

    public boolean isVisible() {
        return locator.isVisible();
    }

    public String text() {
        return locator.textContent().trim();
    }

    public boolean haveClass(String classPart) {
        String classes = getAttribute("class");
        if (classes != null) {
            return classes.contains(classPart);
        } else return false;
    }
}