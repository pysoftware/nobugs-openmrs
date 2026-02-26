package ui.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LocationPage extends BasePage {
    public LocationPage(Page page) {
        super(page);
    }

    @Override
    protected String getRelativePath() {
        return "openmrs/spa/login/location";
    }

    /**
     * Выбирает первую доступную локацию и нажимает Continue
     * Возвращает следующую страницу (например HomePage)
     */
    public void selectFirstLocationAndContinue() {
        // Ждём появления списка локаций
        page.waitForSelector("[role=\"option\"]",
                new Page.WaitForSelectorOptions().setTimeout(15000));

        // Выбираем первую локацию
        page.getByRole(AriaRole.valueOf("option")).first().click();

        // Нажимаем Continue
        page.getByRole(AriaRole.valueOf("button"), new Page.GetByRoleOptions().setName("Continue")).click();


    }
}
