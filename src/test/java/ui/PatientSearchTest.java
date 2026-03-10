package ui;

import annotations.AdminSession;
import api.generators.TestDataFakerGenerator;
import api.models.PatientIdentifierTypeResponse;
import api.models.PatientResponse;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.components.common.Header;
import ui.pages.HomePage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static api.requests.steps.PatientSteps.getPatientsByName;
import static api.requests.steps.PatientSteps.hasPatient;
import static api.utils.StringUtils.parseDisplay;

public class PatientSearchTest extends BaseUiTest {
    private Header header;

    @BeforeEach
    void setup() {
        HomePage homePage = new HomePage(page).open();
        header = homePage.header;
    }

    @AdminSession
    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanSearchPatientById() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);

        Map<String, String> identifiers = identifiers(patient);
        String identifierRequired = identifierRequired(identifiers);

        List<String> actualIdentifiers = header.searchPatientByNameOrId(identifierRequired)
                .clickBanner(0)
                .getIdentifiers();

        softly.assertThat(actualIdentifiers).containsExactlyInAnyOrderElementsOf(identifiers.keySet().stream().toList());
        softly.assertThat(hasPatient(patient.getUuid())).isNotNull();
    }

    @AdminSession
    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanSearchPatientByName() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        String expectedName = patient.getPerson().getPreferredName().getDisplay();

        String actualName = header.searchPatientByNameOrId(expectedName)
                .clickBanner(expectedName)
                .getName();

        softly.assertThat(actualName).isEqualTo(expectedName);
        softly.assertThat(getPatientsByName(actualName)).isNotNull();
    }

    @AdminSession
    @Test
    public void adminCanNotSearchPatientByNonExistentName() {
        TestDataFakerGenerator faker = new TestDataFakerGenerator();
        String nonExistentName = faker.generateGivenName();
        int count = header.searchPatientByNameOrId(nonExistentName)
                .countSearchResults();

        softly.assertThat(count).isZero();
        softly.assertThat(getPatientsByName(nonExistentName)).hasSize(0);
    }

    @AdminSession
    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanOpenPatientAction() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        String expectedName = patient.getPerson().getPreferredName().getDisplay();
        header.searchPatientByNameOrId(expectedName)
                .openActions();

        /* softly.assertThat(expectedName).isEqualTo(actualName);
        softly.assertThat(getPatientsByName(actualName)).isNotNull();*/
    }

    private Map<String, String> identifiers(PatientResponse patient) {
        return patient.getIdentifiers().stream()
                .map(identifier -> parseDisplay(identifier.getDisplay(), "="))
                .collect(Collectors.toMap(identifier -> identifier[1], identifier -> identifier[0]));
    }

    private String identifierRequired(Map<String, String> identifiers) {
        List<String> identifierNotRequired =
                SessionStorage.getAll(Prepare.PATIENT_IDENTIFIER_TYPE, PatientIdentifierTypeResponse.class).stream()
                        .map(PatientIdentifierTypeResponse::getDisplay)
                        .toList();

        return identifiers.entrySet().stream()
                .filter(e -> !identifierNotRequired.contains(e.getValue()))
                .map(Map.Entry::getKey).toList().getFirst();
    }
}
