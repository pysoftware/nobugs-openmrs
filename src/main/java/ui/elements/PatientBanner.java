package ui.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;


public class PatientBanner extends BaseElement {

    private final Locator nameLink;
    private final Locator actionsButton;
    private final Locator startVisitButton;
    private final Locator showMoreButton;

    public PatientBanner(Locator root) {
        super(root);
        this.nameLink = root.locator("a[href*='/chart/']");
        this.actionsButton = root.getByRole(AriaRole.BUTTON,
                new Locator.GetByRoleOptions().setName("Actions")
        );
        this.startVisitButton = root.getByRole(AriaRole.BUTTON,
                new Locator.GetByRoleOptions().setName("Start visit")
        );
        this.showMoreButton = root.getByRole(AriaRole.BUTTON,
                new Locator.GetByRoleOptions().setName("Show more")
        );
    }

    public void clickPatientName() {
        nameLink.click();
    }

    public void startVisit() {
        startVisitButton.click();
    }

    public void openActions() {
        actionsButton.click();
    }

    public void clickShowMore() {
        showMoreButton.click();
    }
}