package api.patient;

import api.BaseTest;
import api.database.dao.PatientDao;
import api.database.dao.comparison.DaoAndModelAssertions;
import api.generators.RandomModelGenerator;
import api.generators.RegexConstants;
import api.models.*;
import api.models.comparison.ModelAssertions;
import api.requests.steps.DataBaseSteps;
import api.requests.steps.LocationSteps;
import api.requests.steps.PatientSteps;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PatientTest extends BaseTest {
    private PatientIdentifierTypeResponse PreparedPatientIdentifierType() {
        // Create PatientIdentifierType
        Map<String, Object> fieldsOfIdentifierType = new HashMap<>(Map.of("format", RegexConstants.PATIENT_IDENTIFIER_TYPE_FORMAT,
                "required", false, "locationBehavior", "NOT_USED"));
        fieldsOfIdentifierType.put("validator", null);
        fieldsOfIdentifierType.put("uniquenessBehavior", null);

        PatientIdentifierTypeCreateRequest identifierTypeCreateRequest = RandomModelGenerator.generate(PatientIdentifierTypeCreateRequest.class, fieldsOfIdentifierType);
        PatientIdentifierTypeResponse patientIdentifierType = PatientSteps.createPatientIdentifierType(identifierTypeCreateRequest);

        ModelAssertions.assertThatModels(identifierTypeCreateRequest, patientIdentifierType).match();
        return patientIdentifierType;
    }

    private LocationResponse PreparedLocation() {
        // Create location
        Map<String, Object> fieldsOfLocation =
                new HashMap<>(Map.of("tags", List.of(), "childLocations", List.of(), "attributes", List.of()));

        LocationCreateRequest locationCreateRequest = RandomModelGenerator.generate(LocationCreateRequest.class, fieldsOfLocation);
        LocationResponse location = LocationSteps.createLocation(locationCreateRequest);

        ModelAssertions.assertThatModels(locationCreateRequest, location).match();
        return location;
    }

    @Test
    public void userCanCreatePatientWithCorrectData() {
        PatientIdentifierTypeResponse patientIdentifierType = PreparedPatientIdentifierType();
        LocationResponse location = PreparedLocation();

        // Create patient
        IdentifierRequest identifierRequest = RandomModelGenerator.generate(IdentifierRequest.class,
                Map.of("identifierType", patientIdentifierType.getUuid(), "location", location.getUuid()));
        Map<String, Object> fieldsOfPatient = new HashMap<>(Map.of("identifiers", List.of(identifierRequest),
                "dead", false));
        fieldsOfPatient.put("causeOfDeath", null);
        fieldsOfPatient.put("deathDate", null);

        PatientCreateNewRequest patientRequest = RandomModelGenerator.generate(PatientCreateNewRequest.class, fieldsOfPatient);
        PatientResponse patient = PatientSteps.createPatient(patientRequest);

        ModelAssertions.assertThatModels(patientRequest, patient).match();

        assertThat(PatientSteps.hasPatient(patient.getUuid())).isNotNull();

        PatientDao patientDao = DataBaseSteps.getPatientByUuid(patient.getUuid());
        DaoAndModelAssertions.assertThat(patient, patientDao).match();
    }
}
