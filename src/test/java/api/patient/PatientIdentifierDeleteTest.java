package api.patient;

import api.BaseTest;
import api.database.dao.CountDao;
import api.database.dao.PatientIdentifierDao;
import api.models.PatientIdentifierResponse;
import api.models.PatientResponse;
import api.requests.steps.DataBaseSteps;
import api.requests.steps.PatientIdentifierSteps;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PatientIdentifierDeleteTest extends BaseTest {
    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanDeleteExistingPatientIdentifier() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        String patientUuid = patient.getUuid();
        String patientIdentifierUUid = patient.getIdentifiers().get(1).getUuid();

        PatientIdentifierSteps.deletePatientIdentifier(true, patientUuid, patientIdentifierUUid);

        PatientIdentifierSteps.getPatientIdentifierThatDoesNotExist(patientUuid, patientIdentifierUUid);

        PatientIdentifierDao patientIdentifierDao = DataBaseSteps.getPatientIdentifierByUuid(patientIdentifierUUid);
        softly.assertThat(patientIdentifierDao).isNull();
    }

    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanDeleteWithMarkExistingPatient() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        String patientUuid = patient.getUuid();
        String patientIdentifierUUid = patient.getIdentifiers().get(1).getUuid();

        PatientIdentifierSteps.deletePatientIdentifier(false, patientUuid, patientIdentifierUUid);

        List<PatientIdentifierResponse> patientIdentifiers = PatientIdentifierSteps.getPatientIdentifiers(patientUuid);
        softly.assertThat(patientIdentifiers).extracting(PatientIdentifierResponse::getIdentifier)
                .doesNotContain(patientIdentifierUUid);

        PatientIdentifierDao patientIdentifierDao = DataBaseSteps.getPatientIdentifierByUuid(patientIdentifierUUid);
        softly.assertThat(patientIdentifierDao.getVoidReason()).isEqualTo(DataBaseSteps.VOID_REASON);
    }

    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCannotDeleteNoExistentPatient() {
        CountDao patientRowsExpected = DataBaseSteps.countRowsOfTable(DataBaseSteps.Table.PATIENT_IDENTIFIER);

        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        String patientUuid = patient.getUuid();

        String uuidNonExistent = RandomStringUtils.randomAlphanumeric(16);
        PatientIdentifierSteps.deletePatientIdentifierFailed(false, patientUuid, uuidNonExistent);

        CountDao patientRowsActual = DataBaseSteps.countRowsOfTable(DataBaseSteps.Table.PATIENT_IDENTIFIER);
        softly.assertThat(patientRowsActual).isEqualTo(patientRowsExpected);

    }
}
