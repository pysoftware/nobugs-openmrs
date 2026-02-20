package api.patient;

import api.BaseTest;
import api.database.dao.CountDao;
import api.database.dao.PatientDao;
import api.models.PatientResponse;
import api.requests.steps.DataBaseSteps;
import api.requests.steps.PatientSteps;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

public class PatientDeleteTest extends BaseTest {
    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanDeleteExistingPatient() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        String name = patient.getDisplay();

        PatientSteps.deletePatient(true, patient.getUuid());

        softly.assertThat(PatientSteps.getPatientsByName(name)).isEmpty();

        PatientDao patientDao = DataBaseSteps.getPatientByUuid(patient.getUuid());
        softly.assertThat(patientDao).isNull();
    }

    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanDeleteWithMarkExistingPatient() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        String name = patient.getDisplay();

        PatientSteps.deletePatient(false, patient.getUuid());

        softly.assertThat(PatientSteps.getPatientsByName(name)).isEmpty();

        PatientDao patientDao = DataBaseSteps.getPatientByUuid(patient.getUuid());
        softly.assertThat(patientDao.getVoidReason()).isEqualTo(DataBaseSteps.VOID_REASON);
    }

    @Test
    public void adminCannotDeleteNoExistentPatient() {
        CountDao patientRowsExpected = DataBaseSteps.countRowsOfTable(DataBaseSteps.Table.PATIENT);

        String name = RandomStringUtils.randomAlphabetic(10);
        PatientSteps.deletePatientFailed(false, name);

        CountDao patientRowsActual = DataBaseSteps.countRowsOfTable(DataBaseSteps.Table.PATIENT);
        softly.assertThat(patientRowsActual).isEqualTo(patientRowsExpected);

    }

    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCanDeletePatientWitDeletionMark() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        String name = patient.getDisplay();

        PatientSteps.deletePatient(false, patient.getUuid());
        softly.assertThat(PatientSteps.getPatientsByName(name)).isEmpty();

        PatientDao patientDao = DataBaseSteps.getPatientByUuid(patient.getUuid());
        softly.assertThat(patientDao.getVoidReason()).isEqualTo(DataBaseSteps.VOID_REASON);

        PatientSteps.deletePatient(true, patient.getUuid());
        patientDao = DataBaseSteps.getPatientByUuid(patient.getUuid());
        softly.assertThat(patientDao).isNull();
    }
}
