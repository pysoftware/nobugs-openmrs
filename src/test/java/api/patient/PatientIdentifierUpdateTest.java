package api.patient;

import api.BaseTest;
import api.database.dao.CountDao;
import api.database.dao.PatientIdentifierDao;
import api.database.dao.comparison.DaoAndModelAssertions;
import api.generators.RandomModelGenerator;
import api.models.PatientIdentifierResponse;
import api.models.PatientIdentifierUpdateRequest;
import api.models.PatientResponse;
import api.models.comparison.ModelAssertions;
import api.requests.steps.DataBaseSteps;
import api.requests.steps.PatientIdentifierSteps;
import api.utils.StringUtils;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

public class PatientIdentifierUpdateTest extends BaseTest {
    @PrepareData(Prepare.PATIENT)
    @PrepareData(Prepare.PATIENT_IDENTIFIER_TYPE)
    @Test
    public void adminCanUpdatePatientIdentifierWithCorrectData() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);

        PatientIdentifierUpdateRequest patientIdentifierRequest =
                RandomModelGenerator.generate(PatientIdentifierUpdateRequest.class);

        String patientUuid = patient.getPerson().getUuid();
        String identifierUuid = patient.getIdentifiers().get(0).getUuid();
        PatientIdentifierResponse patientIdentifierResponse = PatientIdentifierSteps.updatePatientIdentifier(
                patientIdentifierRequest, patientUuid, identifierUuid);

        ModelAssertions.assertThatModels(patientIdentifierRequest, patientIdentifierResponse).match();

        PatientIdentifierResponse patientIdentifier = PatientIdentifierSteps.getPatientIdentifier(patientUuid, identifierUuid);
        softly.assertThat(patientIdentifier).isNotNull();

        PatientIdentifierDao patientIdentifierDao = DataBaseSteps.getPatientIdentifierByUuid(patientIdentifierResponse.getUuid());
        DaoAndModelAssertions.assertThat(patientIdentifierResponse, patientIdentifierDao).match();
    }

    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCannotUpdatePatientIdentifierWithIncorrectData() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);

        PatientIdentifierUpdateRequest patientIdentifierRequest =
                RandomModelGenerator.generate(PatientIdentifierUpdateRequest.class,
                        fields -> fields.setIdentifier(RandomStringUtils.randomAlphabetic(10)));

        String patientUuid = patient.getPerson().getUuid();
        String identifierUuid = patient.getIdentifiers().get(0).getUuid();
        String identifier = StringUtils.parseDisplay(patient.getIdentifiers().get(0).getDisplay(), "=")[1];
        PatientIdentifierSteps.updatePatientIdentifierFailed(patientIdentifierRequest, patientUuid, identifierUuid);

        PatientIdentifierResponse patientIdentifier = PatientIdentifierSteps.getPatientIdentifier(patientUuid, identifierUuid);
        softly.assertThat(patientIdentifier.getIdentifier()).isEqualTo(identifier);

        PatientIdentifierDao patientIdentifierDao = DataBaseSteps.getPatientIdentifierByUuid(identifierUuid);
        softly.assertThat(patientIdentifierDao.getIdentifier()).isEqualTo(identifier);
    }

    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCannotUpdateNoExistentPatientIdentifier() {
        CountDao patientRowsExpected = DataBaseSteps.countRowsOfTable(DataBaseSteps.Table.PATIENT_IDENTIFIER);

        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        String uuidNonExistent = RandomStringUtils.randomAlphanumeric(16);

        PatientIdentifierUpdateRequest patientIdentifierRequest = RandomModelGenerator.generate(PatientIdentifierUpdateRequest.class);

        String patientUuid = patient.getPerson().getUuid();
        PatientIdentifierSteps.updateNoExistentPatientIdentifierFailed(patientIdentifierRequest, patientUuid, uuidNonExistent);

        CountDao patientRowsActual = DataBaseSteps.countRowsOfTable(DataBaseSteps.Table.PATIENT_IDENTIFIER);
        softly.assertThat(patientRowsActual).isEqualTo(patientRowsExpected);
    }
}
