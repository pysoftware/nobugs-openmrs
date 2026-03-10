package ui.elements;

import com.microsoft.playwright.Locator;

public class TextInput extends PageElement {
    public TextInput(Locator locator) {
        super(locator);
    }

    public void fill(String value) {
        locator.fill(value);
    }

    public String value() {
        return locator.inputValue();
    }

    private void blur() {
        locator.evaluate("el => el.blur()");
    }

    public void fillAndBlur(String value) {
        fill(value);
        blur();
    }

    public void press(String key){
        locator.press(key);
    }
}
