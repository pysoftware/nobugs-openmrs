package ui.pages;

import com.microsoft.playwright.Page;
import ui.components.patient_registration.BasicInfoComponent;
import ui.selectors.PatientRegistrationSelectors;

public class PatientRegistrationPage extends BasePage<PatientRegistrationPage> {
    public final BasicInfoComponent basicInfoComponent;

    public PatientRegistrationPage(Page page) {
        super(page);
        basicInfoComponent = new BasicInfoComponent(page.locator(PatientRegistrationSelectors.DEMOGRAPHICS));
    }

    @Override
    protected String path() {
        return "patient-registration";
    }
}
