package ui.components.patient_summary;

import com.microsoft.playwright.Locator;
import ui.components.BaseForm;
import ui.elements.Button;
import ui.selectors.PatientSummarySelectors;

public class PatientBanner extends BaseForm {
    private final Locator name;
    public final Locator identifierRequired;
    public final Locator identifierNotRequired;

    public final Button actionsButton;
    public final ActionsMenu actionsMenu;

    public PatientBanner(Locator locator) {
        super(locator);

        name = locator.locator("svg").locator(PatientSummarySelectors.NAME);
        identifierRequired = locator.locator(PatientSummarySelectors.IDENTIFIER_REQUIRED);
        identifierNotRequired = locator.locator(PatientSummarySelectors.IDENTIFIER_NOT_REQUIRED);

        actionsButton = new Button(locator, "Actions");
        actionsMenu = new ActionsMenu(locator.locator(PatientSummarySelectors.ACTION_MENU));
    }

    public String getName() {
        return name.textContent().trim();
    }

    public ActionsMenu openActions() {
        actionsButton.click();
        return new ActionsMenu(actionsMenu.locator);
    }
}
