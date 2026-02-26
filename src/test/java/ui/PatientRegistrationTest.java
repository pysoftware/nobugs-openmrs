package ui;

import annotations.AdminSession;
import org.junit.jupiter.api.Test;
import ui.pages.PatientRegistrationPage;

public class PatientRegistrationTest extends BaseUiTest {
    @AdminSession
    @Test
    void adminCanRegisterPatient() {
        new PatientRegistrationPage(page).open();
    }

}
