package api.patient;

import api.BaseTest;
import api.database.dao.PatientDao;
import api.database.dao.comparison.DaoAndModelAssertions;
import api.generators.RandomModelGenerator;
import api.models.*;
import api.models.comparison.ModelAssertions;
import api.requests.steps.DataBaseSteps;
import api.requests.steps.PatientSteps;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PatientTest extends BaseTest {
    @PrepareData(Prepare.PATIENT_IDENTIFIER_TYPE)
    @PrepareData(Prepare.LOCATION)
    @Test
    public void userCanCreatePatientWithCorrectData() {
        PatientIdentifierTypeResponse patientIdentifierType = SessionStorage.get(Prepare.PATIENT_IDENTIFIER_TYPE, 1);
        LocationResponse location = SessionStorage.get(Prepare.LOCATION, 1);
        // Create patient
        PatientCreateNewRequest patientRequest =
                RandomModelGenerator.generate(PatientCreateNewRequest.class,
                        fields -> {
                            for (IdentifierRequest identifier : fields.getIdentifiers()) {
                                identifier.setIdentifierType(patientIdentifierType.getUuid());
                                identifier.setLocation(location.getUuid());
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
}
