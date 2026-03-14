package ui.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import ui.components.common.Header;

public abstract class BasePage<T extends BasePage<T>> {
    protected final Page page;
    public final Header header;

    protected BasePage(Page page) {
        this.page = page;
        this.header = new Header(page.getByRole(AriaRole.BANNER));
    }

    public T open() {
        page.navigate(path());
        return (T) this;
    }

    protected abstract String path();
}
