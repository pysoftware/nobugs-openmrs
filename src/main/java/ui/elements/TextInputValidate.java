package ui.elements;

import com.microsoft.playwright.Locator;

public class TextInputValidate extends TextInput {
    private final PageElement warning;

    public TextInputValidate(Locator locator, String fieldId) {
        super(locator.locator(fieldId));
        this.warning = new PageElement(locator.locator(fieldId + "-warn-msg"));
    }

    public String getWarning() {
        return warning.text();
    }
}
