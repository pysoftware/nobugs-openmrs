package ui;

import api.BaseTest;
import api.configs.Config;
import api.specs.RequestSpec;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Cookie;
import extensions.AdminSessionExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import ui.configs.PlaywrightConfiguration;
import ui.pages.LocationPage;

import java.util.List;

@ExtendWith(AdminSessionExtension.class)
public class BaseUiTest extends BaseTest {
    protected static Playwright playwright;
    protected static String url;
    protected static Browser browser;
    protected Page page;

    @BeforeAll
    static void setupPlaywright() {
        // Один раз загружаем настройки
        PlaywrightConfiguration.loadFromProperties();

        url = PlaywrightConfiguration.baseUrl + PlaywrightConfiguration.basePath;
        playwright = Playwright.create();

        BrowserType browserType = switch (PlaywrightConfiguration.browser.toLowerCase()) {
            case "firefox" -> playwright.firefox();
            case "webkit", "safari" -> playwright.webkit();
            default -> playwright.chromium();
        };

        boolean headless = System.getenv("CI") != null;

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(headless && PlaywrightConfiguration.headless);

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
            case "chrome" -> "chrome";
            case "edge", "msedge" -> "msedge";
            default -> null;
        };
    }

    /**
     * Авторизует администратора (admin) через REST API и внедряет сессию в браузер Playwright.
     * Использует готовую спецификацию adminSpec(), которая уже кэширует JSESSIONID.
     */
    public void authAsAdmin() {
        // Получаем спецификацию для admin (она сама залогинится, если сессия не активна)
        var adminSpec = RequestSpec.adminSpec();

        String jsessionId = RequestSpec.getJsessionId(Config.getProperty("admin.username"), Config.getProperty("admin.password"));  // реализация ниже

        if (jsessionId == null || jsessionId.isBlank()) {
            throw new IllegalStateException("Не удалось получить JSESSIONID для admin");
        }

        // Создаём новый контекст браузера
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions().setBaseURL(url);

        try {
            String[] dims = PlaywrightConfiguration.browserSize.split("x");
            int w = Integer.parseInt(dims[0].trim());
            int h = Integer.parseInt(dims[1].trim());
            contextOptions.setViewportSize(w, h);
        } catch (Exception ignored) {
            // можно залогировать
        }

        BrowserContext context = browser.newContext(contextOptions);

        // Кладём JSESSIONID в контекст
        context.addCookies(List.of(
                new Cookie("JSESSIONID", jsessionId)
                        .setDomain("localhost")
                        .setPath("/")
                        .setHttpOnly(true)
                        .setSecure(false)
        ));

        // Создаём page после контекста
        page = context.newPage();

        // Выбираем локацию чтобы можно было приступить к работе
        new LocationPage(page).selectFirstLocationAndConfirm();
    }
}
