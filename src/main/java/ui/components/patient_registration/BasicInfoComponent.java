package ui.components.patient_registration;

import com.microsoft.playwright.Locator;
import ui.components.BaseForm;
import ui.elements.RadioGroup;
import ui.elements.TextInputValidate;
import ui.enums.Sex;
import ui.selectors.PatientRegistrationSelectors;

public class BasicInfoComponent extends BaseForm {
    public final TextInputValidate firstName;
    public final TextInputValidate middleName;
    public final TextInputValidate familyName;

    private final RadioGroup<Sex> sexGroup;

    public BasicInfoComponent(Locator locator) {
        super(locator);

        firstName = new TextInputValidate(locator, PatientRegistrationSelectors.FIRST_NAME);
        middleName = new TextInputValidate(locator, PatientRegistrationSelectors.MIDDLE_NAME);
        familyName = new TextInputValidate(locator, PatientRegistrationSelectors.FAMILY_NAME);

        sexGroup = new RadioGroup<>(locator, Sex.class);
    }

    public void selectSex(Sex sex) {
        sexGroup.select(sex);
    }

    public void selectRandomSex() {
        sexGroup.selectRandom();
    }
}
