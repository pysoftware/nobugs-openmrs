package ui;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Helpers {
    // Common
    public static void waitForLocatorUpdate(Locator locator, int timeoutMillis) {
        // Получаем текущее содержимое контейнера
        String initialHtml = locator.innerHTML();

        // Ждём изменения содержимого
        locator.page().waitForCondition(() -> {
            String currentHtml = locator.innerHTML();
            return !currentHtml.equals(initialHtml);
        }, new Page.WaitForConditionOptions().setTimeout(timeoutMillis));
    }

    // SearchPage
    public static String getPatientUuid(Locator locator) {
        Locator link = locator.locator("a[href*='/patient/'][href$='/chart/']");
        String href = link.getAttribute("href");
        return href.split("/patient/")[1].split("/")[0];
    }
}
