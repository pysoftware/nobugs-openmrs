package api.patient;

import api.BaseTest;
import api.database.dao.CountDao;
import api.database.dao.PatientIdentifierDao;
import api.database.dao.comparison.DaoAndModelAssertions;
import api.generators.RandomModelGenerator;
import api.models.*;
import api.models.comparison.ModelAssertions;
import api.requests.steps.DataBaseSteps;
import api.requests.steps.PatientIdentifierSteps;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PatientIdentifierCreateTest extends BaseTest {
    @PrepareData(Prepare.PATIENT)
    @PrepareData(Prepare.PATIENT_IDENTIFIER_TYPE)
    @PrepareData(Prepare.LOCATION)
    @Test
    public void adminCanCreatePatientIdentifierWithCorrectData() {
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);
        PatientIdentifierTypeResponse patientIdentifierType = SessionStorage.get(Prepare.PATIENT_IDENTIFIER_TYPE, 1);
        LocationResponse location = SessionStorage.get(Prepare.LOCATION, 1);

        PatientIdentifierCreateRequest patientIdentifierRequest =
                RandomModelGenerator.generate(PatientIdentifierCreateRequest.class,
                        fields -> {
                            fields.setIdentifierType(patientIdentifierType.getUuid());
                            fields.setLocation(location.getUuid());
                            fields.setPreferred(false);
                        });
        PatientIdentifierResponse patientIdentifierResponse = PatientIdentifierSteps.createPatientIdentifier(patientIdentifierRequest, patient.getPerson().getUuid());
        ModelAssertions.assertThatModels(patientIdentifierRequest, patientIdentifierResponse).match();

        List<PatientIdentifierResponse> patientIdentifier = PatientIdentifierSteps.getPatientIdentifiers(patient.getUuid());
        softly.assertThat(patientIdentifier).anyMatch(p->p.getIdentifier().equals(patientIdentifierRequest.getIdentifier()));
        softly.assertThat(patientIdentifier).anyMatch(p->p.getIdentifierType().getUuid().equals(patientIdentifierRequest.getIdentifierType()));
        softly.assertThat(patientIdentifier).anyMatch(p->p.getLocation().getUuid().equals(patientIdentifierRequest.getLocation()));
        softly.assertThat(patientIdentifier).anyMatch(p->p.getPreferred().equals(patientIdentifierRequest.getPreferred()));

        PatientIdentifierDao patientIdentifierDao = DataBaseSteps.getPatientIdentifierByUuid(patientIdentifierResponse.getUuid());
        DaoAndModelAssertions.assertThat(patientIdentifierResponse, patientIdentifierDao).match();
    }

    @PrepareData(Prepare.PATIENT)
    @Test
    public void adminCannotCreatePatientIdentifierWithIncorrectData() {
        CountDao patientIdentifierRowsExpected = DataBaseSteps.countRowsOfTable(DataBaseSteps.Table.PATIENT_IDENTIFIER);
        PatientResponse patient = SessionStorage.get(Prepare.PATIENT, 1);

        PatientIdentifierCreateRequest patientIdentifierRequest = RandomModelGenerator.generate(PatientIdentifierCreateRequest.class);

        int countPatientIdentifierExpected = PatientIdentifierSteps.getPatientIdentifiers(patient.getUuid()).size();

        PatientIdentifierSteps.createPatientIdentifierFailed(patientIdentifierRequest, patient.getPerson().getUuid());

        int countPatientIdentifierActual = PatientIdentifierSteps.getPatientIdentifiers(patient.getUuid()).size();
        softly.assertThat(countPatientIdentifierActual).isEqualTo(countPatientIdentifierExpected);

        CountDao patientIdentifierRowsActual = DataBaseSteps.countRowsOfTable(DataBaseSteps.Table.PATIENT_IDENTIFIER);
        softly.assertThat(patientIdentifierRowsActual).isEqualTo(patientIdentifierRowsExpected);
    }
}
