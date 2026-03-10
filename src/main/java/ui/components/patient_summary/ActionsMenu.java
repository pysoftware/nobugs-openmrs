package ui.components.patient_summary;

import com.microsoft.playwright.Locator;
import ui.components.BaseForm;
import ui.selectors.PatientSummarySelectors;

public class ActionsMenu extends BaseForm {
    private final Locator addToList;
    private final Locator editPatientDetails;
    private final Locator addVisit;
    private final Locator markPatientDeceased;

    public ActionsMenu(Locator locator) {
        super(locator);

        addToList = locator.locator(PatientSummarySelectors.ADD_TO_LIST);
        editPatientDetails = locator.locator(PatientSummarySelectors.EDIT_PATIENT_DETAILS);
        addVisit = locator.locator(PatientSummarySelectors.ADD_VISIT);
        markPatientDeceased = locator.locator(PatientSummarySelectors.MARK_PATIENT_DECEASED);
    }

    public void addVisit() {
        addVisit.click();
    }
}
