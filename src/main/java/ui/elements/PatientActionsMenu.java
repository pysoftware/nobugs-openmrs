package ui.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;

public class PatientActionsMenu extends BaseElement {
    private final Locator menuContainer;
    private final Locator addToPatientListButton;
    private final Locator printIdentifierSticker;
    private final Locator startVisitButton;
    private final Locator stopVisitButton;
    private final Locator markAliveButton;
    private final Locator deleteVisitButton;
    private final Locator markDeceasedButton;
    private final Locator editPatientDetailsButton;

    public PatientActionsMenu(Locator root) {
        super(root);
        root.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        this.menuContainer = root;
        if (!menuContainer.isVisible()) {
            throw new IllegalStateException("Cannot find overflow menu container");
        }
        this.addToPatientListButton = menuContainer.locator("[data-extension-id='add-patient-to-patient-list-button']");
        this.printIdentifierSticker = menuContainer.locator("[data-extension-id='print-identifier-sticker-button']");
        this.startVisitButton = menuContainer.locator("[data-extension-id='start-visit-button']");
        this.stopVisitButton = menuContainer.locator("[data-extension-id='stop-visit-button']");
        this.markAliveButton = menuContainer.locator("[data-extension-id='mark-alive-button']");
        this.deleteVisitButton = menuContainer.locator("[data-extension-id='delete-visit-button']");
        this.markDeceasedButton = menuContainer.locator("[data-extension-id='mark-deceased-button']");
        this.editPatientDetailsButton = menuContainer.locator("[data-extension-id='edit-patient-details-button']");
    }

    public void clickStartVisit() {
        startVisitButton.click();
    }

    public void clickStopVisit() {
        stopVisitButton.click();
    }

    public void clickMarkAsDeceased() {
        markDeceasedButton.click();
    }

    public void clickMarkAlive() {
        markAliveButton.click();
    }

    public void clickEditPatientDetails() {
        editPatientDetailsButton.click();
    }

    public void clickDeleteVisit() {
        deleteVisitButton.click();
    }

    public void clickPrintIdentifierSticker() {
        printIdentifierSticker.click();
    }

    public void clickAddToPatientList() {
        addToPatientListButton.click();
    }

    public boolean isStartVisitVisible() {
        return startVisitButton.isVisible();
    }

    public boolean isStopVisitVisible() {
        return stopVisitButton.isVisible();
    }

    public boolean isMarkDeceasedVisible() {
        return markDeceasedButton.isVisible();
    }

    public boolean isOpened() {
        return menuContainer.isVisible();
    }
}
