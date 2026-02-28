package ui.elements;

import com.microsoft.playwright.Locator;

public abstract class BaseElement {
    protected final Locator root;
    public BaseElement(Locator root) { this.root = root; }
}
