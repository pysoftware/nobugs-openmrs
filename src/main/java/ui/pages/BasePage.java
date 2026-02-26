package ui.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;


public abstract class BasePage {

    protected final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    public void open() {
        navigate(getRelativePath());
    }

    protected abstract String getRelativePath();

    protected void waitForVisible(String selector, int timeoutMs) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeoutMs));
    }

    protected void click(String selector) {
        page.click(selector);
    }

    protected void fill(String selector, String value) {
        page.fill(selector, value);
    }

    protected boolean isVisible(String selector) {
        return page.isVisible(selector);
    }

    protected void navigate(String pathOrUrl) {
        if (pathOrUrl == null || pathOrUrl.isBlank()) {
            throw new IllegalArgumentException("Path or URL cannot be null or empty");
        }

        page.navigate(pathOrUrl);

        // ждём стабильной загрузки DOM
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }


}
