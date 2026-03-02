package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import ui.elements.PatientActionsMenu;

import java.util.Objects;

public class PatientSummaryPage extends BasePage<PatientSummaryPage> {

    private final Locator TEXT_ID;
    private final Locator TEXT_NAME;
    private final Locator ACTIONS_BUTTON;
    private PatientActionsMenu actionsMenu;


    public PatientSummaryPage(Page page, String patientUuid) {
        super(page);
        this.patientUuid = Objects.requireNonNull(patientUuid, "patientUuid is required");
        this.TEXT_ID = page.locator("label[class*='label'][for*='identifier'] span:last-child");
        this.TEXT_NAME = page.locator(".sb-avatar__text");
        this.ACTIONS_BUTTON = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Actions")
        );

    }

    @Override
    protected String path() {

        return String.format("patient/%s/chart/Patient%%20Summary", patientUuid);
    }

    public String getPatientId() {
        return TEXT_ID
                .first()
                .textContent()
                .trim();
    }

    public String getPatientName() {
        return TEXT_NAME.getAttribute("title");
    }

    public PatientSummaryPage openActions() {
        ACTIONS_BUTTON.click();
        Locator menu = page.locator("ul[class*='overflow-menu-options__content']");
        menu.waitFor();
        this.actionsMenu = new PatientActionsMenu(menu);
        return this;
    }

    public PatientActionsMenu getActionsMenu() {
        if (actionsMenu == null) {
            throw new IllegalStateException("Actions menu not opened. Call openActions() first.");
        }
        return actionsMenu;
    }

}
