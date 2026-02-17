package api;

import api.database.dao.PatientIdentifierTypeDao;
import api.database.dao.comparison.DaoAndModelAssertions;
import api.generators.RandomModelGenerator;
import api.generators.RegexConstants;
import api.models.PatientIdentifierTypeCreateRequest;
import api.models.PatientIdentifierTypeResponse;
import api.models.comparison.ModelAssertions;
import api.models.enums.LocationBehavior;
import api.requests.steps.DataBaseSteps;
import api.requests.steps.PatientIdentifierTypeSteps;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PatientIdentifierTypeTest extends BaseTest {
    @Test
    public void createPatientIdentifierType() {
        PatientIdentifierTypeCreateRequest identifierTypeCreateRequest =
                RandomModelGenerator.generate(PatientIdentifierTypeCreateRequest.class,
                        fields -> {
                            fields.setFormat(RegexConstants.PATIENT_IDENTIFIER_TYPE_FORMAT);
                            fields.setRequired(false);
                            fields.setLocationBehavior(LocationBehavior.NOT_USED);
                            fields.setValidator(null);
                            fields.setUniquenessBehavior(null);
                        });
        PatientIdentifierTypeResponse patientIdentifierType =
                PatientIdentifierTypeSteps.createPatientIdentifierType(identifierTypeCreateRequest);

        ModelAssertions.assertThatModels(identifierTypeCreateRequest, patientIdentifierType).match();

        assertThat(PatientIdentifierTypeSteps.hasPatientIdentifierType(patientIdentifierType.getUuid())).isNotNull();

        PatientIdentifierTypeDao patientIdentifierTypeDao = DataBaseSteps.getPatientIdentifierTypeByUuid(patientIdentifierType.getUuid());
        DaoAndModelAssertions.assertThat(patientIdentifierType, patientIdentifierTypeDao).match();
    }
}
