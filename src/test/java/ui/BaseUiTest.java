package ui;

import api.BaseTest;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ui.configs.PlaywrightConfiguration;

import java.util.List;

public class BaseUiTest extends BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected Page page;

    @BeforeAll
    static void setupPlaywright() {
        // Один раз загружаем настройки
        PlaywrightConfiguration.loadFromProperties();

        playwright = Playwright.create();

        BrowserType browserType = switch (PlaywrightConfiguration.browser.toLowerCase()) {
            case "firefox"       -> playwright.firefox();
            case "webkit", "safari" -> playwright.webkit();
            default              -> playwright.chromium();
        };

        var options = new BrowserType.LaunchOptions()
                .setHeadless(PlaywrightConfiguration.headless);

        if (browserType == playwright.chromium()) {
            String channel = getBrowserChannel(PlaywrightConfiguration.browser);
            if (channel != null) {
                options.setChannel(channel);
            }
        }

        options.setArgs(List.of(
                "--window-size=" + PlaywrightConfiguration.browserSize.replace("x", ","),
                "--no-sandbox",
                "--disable-dev-shm-usage"
        ));

        browser = browserType.launch(options);
    }

    @BeforeEach
    void createPageAndContext() {
        String baseUrl = PlaywrightConfiguration.baseUrl;

        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();

        try {
            String[] dims = PlaywrightConfiguration.browserSize.split("x");
            int w = Integer.parseInt(dims[0].trim());
            int h = Integer.parseInt(dims[1].trim());
            contextOptions.setViewportSize(w, h);
        } catch (Exception ignored) {
            // можно залогировать
        }

        if (baseUrl != null && !baseUrl.isBlank()) {
            contextOptions.setBaseURL(baseUrl);
        }

        BrowserContext context = browser.newContext(contextOptions);
        page = context.newPage();
    }

    @AfterEach
    void closePage() {
        if (page != null) {
            page.context().close();
            page = null;
        }
    }

    @AfterAll
    static void teardownPlaywright() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    private static String getBrowserChannel(String name) {
        return switch (name.toLowerCase()) {
            case "chrome"  -> "chrome";
            case "edge", "msedge" -> "msedge";
            default -> null;
        };
    }
}
