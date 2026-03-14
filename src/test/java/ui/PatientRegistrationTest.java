package ui;

import annotations.AdminSession;
import com.mifmif.common.regex.Generex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.components.patient_registration.BasicInfoComponent;
import ui.elements.TextInputValidate;
import ui.generators.RegexConstants;
import ui.pages.PatientRegistrationPage;

public class PatientRegistrationTest extends BaseUiTest {
    private PatientRegistrationPage patientRegistrationPage;
    private final String NUMBER_IN_NAME_IS_DUBIOUS = "Number in name is dubious";

    @BeforeEach
    void setup() {
        patientRegistrationPage = new PatientRegistrationPage(page).open();
    }

    @AdminSession
    @Test
    void checkValidationForBasicInfo() {
        BasicInfoComponent basicInfo = patientRegistrationPage.basicInfoComponent;

        checkValidationField(basicInfo.firstName);
        checkValidationField(basicInfo.middleName);
        checkValidationField(basicInfo.familyName);
    }

    private void checkValidationField(TextInputValidate input) {
        String incorrectName = new Generex(RegexConstants.INCORRECT_NAME).random();
        input.fillAndBlur(incorrectName);

        softly.assertThat(input.isVisible()).isTrue();
        softly.assertThat(input.getWarning()).isEqualTo(NUMBER_IN_NAME_IS_DUBIOUS);
    }

    @AdminSession
    @Test
    void adminCanRegisterPatient() {
        BasicInfoComponent basicInfo = patientRegistrationPage.basicInfoComponent;

        basicInfo.firstName.fillAndBlur(RegexConstants.CORRECT_NAME);
        basicInfo.familyName.fillAndBlur(RegexConstants.CORRECT_NAME);
        basicInfo.selectRandomSex();
    }
}
