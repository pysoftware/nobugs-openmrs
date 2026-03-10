package ui.pages;

import com.microsoft.playwright.Page;
import ui.components.patient_summary.ActionsMenu;
import ui.components.patient_summary.PatientBanner;
import ui.selectors.PatientSummarySelectors;

import java.util.ArrayList;
import java.util.List;

public class PatientSummaryPage extends BasePage<PatientSummaryPage> {
    private final String patientUuid;
    public final PatientBanner patientBanner;

    public PatientSummaryPage(Page page, String patientUuid) {
        super(page);
        this.patientUuid = patientUuid;
        patientBanner = new PatientBanner(page.locator(PatientSummarySelectors.PATIENT_BANNER));
    }

    public PatientSummaryPage waitPage() {
        patientBanner.identifierNotRequired.first().waitFor();
        return this;
    }

    @Override
    protected String path() {
        return String.format("patient/%s/chart/Patient%%20Summary", patientUuid);
    }

    public List<String> getIdentifiers() {
        List<String> identifiers = new ArrayList<>();
        identifiers.add(getIdentifierRequired());
        identifiers.addAll(getIdentifiersNotRequired());
        return identifiers;
    }

    public String getName() {
        return patientBanner.getName();
    }

    public String getIdentifierRequired() {
        return patientBanner.identifierRequired.innerText();
    }

    public List<String> getIdentifiersNotRequired() {
        return patientBanner.identifierNotRequired.allInnerTexts();
    }

    public ActionsMenu openActions() {
        return patientBanner.openActions();
    }
}
