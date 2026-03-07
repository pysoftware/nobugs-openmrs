package api.patient;

import api.BaseTest;
import api.database.dao.CountDao;
import api.database.dao.PatientDao;
import api.database.dao.comparison.DaoAndModelAssertions;
import api.generators.RandomModelGenerator;
import api.generators.annotations.openmrs.OpenmrsIdGenerator;
import api.models.*;
import api.models.comparison.ModelAssertions;
import api.requests.steps.DataBaseSteps;
import api.requests.steps.PatientIdentifierTypeSteps;
import api.requests.steps.PatientSteps;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PatientCreateTest extends BaseTest {
    @PrepareData(Prepare.PATIENT_IDENTIFIER_TYPE)
    @PrepareData(Prepare.LOCATION)
    @Test
    public void adminCanCreatePatientWithCorrectData() {
        PatientIdentifierTypeResponse patientIdentifierType = SessionStorage.get(Prepare.PATIENT_IDENTIFIER_TYPE, 1);
        LocationResponse location = SessionStorage.get(Prepare.LOCATION, 1);

        PatientCreateNewRequest patientRequest =
                RandomModelGenerator.generate(PatientCreateNewRequest.class,
                        fields -> {
                            for (IdentifierRequest identifier : fields.getIdentifiers()) {
                                identifier.setIdentifierType(patientIdentifierType.getUuid());
                                identifier.setLocation(location.getUuid());
                                identifier.setPreferred(false);
                            }
                            Person person = fields.getPerson();
                            person.setDead(false);
                            person.setCauseOfDeath(null);
                            person.setDeathDate(null);
                        });
        PatientResponse patient = PatientSteps.createPatient(patientRequest);

        ModelAssertions.assertThatModels(patientRequest, patient).match();

        assertThat(PatientSteps.hasPatient(patient.getUuid())).isNotNull();

        PatientDao patientDao = DataBaseSteps.getPatientByUuid(patient.getUuid());
        DaoAndModelAssertions.assertThat(patient, patientDao).match();
    }

    @Test
    public void adminCannotCreatePatientWithIncorrectData() {
        CountDao patientRowsExpected = DataBaseSteps.countRowsOfTable(DataBaseSteps.Table.PATIENT);
        PatientCreateNewRequest patientRequest = RandomModelGenerator.generate(PatientCreateNewRequest.class);

        String name = patientRequest.getPerson().getNames().get(0).getGivenName();
        int countPatientExpected = PatientSteps.getPatientsByName(name).size();

        PatientSteps.createPatientFailed(patientRequest);

        int countPatientActual = PatientSteps.getPatientsByName(name).size();
        softly.assertThat(countPatientActual).isEqualTo(countPatientExpected);

        CountDao patientRowsActual = DataBaseSteps.countRowsOfTable(DataBaseSteps.Table.PATIENT);
        softly.assertThat(patientRowsActual).isEqualTo(patientRowsExpected);
    }

    @PrepareData(Prepare.PATIENT_IDENTIFIER_TYPE)
    @PrepareData(Prepare.LOCATION)
    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanCreatePatientFromExistingPersonWithCorrectData() {
        PatientIdentifierTypeResponse patientIdentifierType = SessionStorage.get(Prepare.PATIENT_IDENTIFIER_TYPE, 1);
        LocationResponse location = SessionStorage.get(Prepare.LOCATION, 1);
        PersonResponse person = SessionStorage.get(Prepare.PERSON, 1);

        List<PatientIdentifierTypeResponse> patientIdentifierTypegetRequiredList =
                PatientIdentifierTypeSteps.getPatientIdentifierTypeList().stream()
                        .filter(PatientIdentifierTypeResponse::getRequired)
                        .toList();

        PatientCreateFromExistingPersonRequest patientRequest =
                RandomModelGenerator.generate(PatientCreateFromExistingPersonRequest.class,
                        fields -> {
                            for (IdentifierRequest identifier : fields.getIdentifiers()) {
                                identifier.setIdentifierType(patientIdentifierType.getUuid());
                                identifier.setLocation(location.getUuid());
                                identifier.setPreferred(false);
                            }
                            for (PatientIdentifierTypeResponse identifier : patientIdentifierTypegetRequiredList) {
                                fields.getIdentifiers().add(new IdentifierRequest(
                                        OpenmrsIdGenerator.generateOpenmrsId(), identifier.getUuid(), location.getUuid(), true)
                                );
                            }
                            fields.setPerson(person.getUuid());
                        });
        PatientResponse patient = PatientSteps.createPatient(patientRequest);

        ModelAssertions.assertThatModels(patientRequest, patient).match();

        assertThat(PatientSteps.hasPatient(patient.getUuid())).isNotNull();

        PatientDao patientDao = DataBaseSteps.getPatientByUuid(patient.getUuid());
        DaoAndModelAssertions.assertThat(patient, patientDao).match();
    }

    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCannotCreatePatientFromExistingPersonWithIncorrectData() {
        CountDao patientRowsExpected = DataBaseSteps.countRowsOfTable(DataBaseSteps.Table.PATIENT);
        PersonResponse person = SessionStorage.get(Prepare.PERSON, 1);

        String name = person.getDisplay();
        int countPatientExpected = PatientSteps.getPatientsByName(name).size();

        PatientCreateFromExistingPersonRequest patientRequest = RandomModelGenerator.generate(PatientCreateFromExistingPersonRequest.class,
                fields -> {
                    fields.setPerson(person.getUuid());
                });
        PatientSteps.createPatientFailed(patientRequest);

        int countPatientActual = PatientSteps.getPatientsByName(name).size();
        softly.assertThat(countPatientActual).isEqualTo(countPatientExpected);

        CountDao patientRowsActual = DataBaseSteps.countRowsOfTable(DataBaseSteps.Table.PATIENT);
        softly.assertThat(patientRowsActual).isEqualTo(patientRowsExpected);
    }
}
