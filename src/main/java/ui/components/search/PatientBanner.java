package ui.components.search;

import com.microsoft.playwright.Locator;
import ui.components.BaseForm;
import ui.elements.Button;

public class PatientBanner extends BaseForm {
    public final Button actionsButton;
    public final Button startVisitButton;
    public final Button showMoreButton;

    public PatientBanner(Locator locator) {
        super(locator);

        actionsButton = new Button(locator, "Actions");
        startVisitButton = new Button(locator, "Start visit");
        showMoreButton = new Button(locator, "Show more");
    }
}