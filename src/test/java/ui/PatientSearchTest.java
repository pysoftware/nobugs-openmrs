package ui;

import annotations.AdminSession;
import api.generators.TestDataFakerGenerator;
import api.models.PatientResponse;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;
import ui.pages.PatientRegistrationPage;

import static api.requests.steps.PatientSteps.getPatientsByName;
import static api.requests.steps.PatientSteps.hasPatient;
import static api.utils.StringUtils.parseDisplay;

public class PatientSearchTest extends BaseUiTest {
    @AdminSession
    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanSearchPatientById() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        String[] ids = parseDisplay(patient.getIdentifiers().getFirst().getDisplay());
        String expectedId = ids[1];
        String actualId = new PatientRegistrationPage(page)
                .searchPatientByIdOrName(expectedId)
                .searchPatient()
                .getOpenMrsId();

        softly.assertThat(expectedId).isEqualTo(actualId);
        softly.assertThat(hasPatient(patient.getUuid())).isNotNull();
    }

    @AdminSession
    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanSearchPatientByName() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        String expectedName = patient.getPerson().getPreferredName().getDisplay();
        String actualName = new PatientRegistrationPage(page).searchPatientByIdOrName(expectedName)
                .searchPatient()
                .getPatientName();

        softly.assertThat(expectedName).isEqualTo(actualName);

        softly.assertThat(getPatientsByName(actualName)).isNotNull();
    }

    @AdminSession
    @Test
    public void adminCanNotSearchPatientByNonExistentName() {
        TestDataFakerGenerator faker = new TestDataFakerGenerator();
        String nonExistentName = faker.generateGivenName();
        new PatientRegistrationPage(page).searchPatientByIdOrName(nonExistentName)
                .patientNotFound();

        softly.assertThat(getPatientsByName(nonExistentName)).hasSize(0);
    }

}
