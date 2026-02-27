package ui.configs;

import api.configs.Config;

public class PlaywrightConfiguration {
    public static String remote;       // для будущего удалённого запуска, если понадобится
    public static String baseUrl;
    public static String basePath;
    public static String browser       = "chrome";
    public static String browserSize   = "1920x1080";
    public static boolean headless     = true;

    // Инициализация из properties — вызывается один раз в @BeforeAll
    public static void loadFromProperties() {
        remote     = Config.getProperty("uiRemote");  // может быть null, если не используем
        baseUrl    = Config.getProperty("uiBaseUrl");
        basePath    = Config.getProperty("uiBasePath");
        browser    = Config.getProperty("browser");
        browserSize = Config.getProperty("browserSize");

        String headlessValue = Config.getProperty("headless");
        if (headlessValue != null) {
            headless = Boolean.parseBoolean(headlessValue);
        }
    }
}
