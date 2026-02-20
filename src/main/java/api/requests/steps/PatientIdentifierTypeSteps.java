package api.requests.steps;

import api.generators.RandomModelGenerator;
import api.generators.RegexConstants;
import api.models.PatientIdentifierTypeCreateRequest;
import api.models.PatientIdentifierTypeResponse;
import api.models.enums.LocationBehavior;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import io.restassured.response.ValidatableResponse;

public class PatientIdentifierTypeSteps {
    public static PatientIdentifierTypeResponse createPatientIdentifierType() {
        PatientIdentifierTypeCreateRequest identifierTypeCreateRequest =
                RandomModelGenerator.generate(PatientIdentifierTypeCreateRequest.class,
                        fields -> {
                            fields.setFormat(RegexConstants.PATIENT_IDENTIFIER_TYPE_FORMAT);
                            fields.setRequired(false);
                            fields.setLocationBehavior(LocationBehavior.NOT_USED);
                            fields.setValidator(null);
                            fields.setUniquenessBehavior(null);
                        });
        return createPatientIdentifierType(identifierTypeCreateRequest);
    }

    public static PatientIdentifierTypeResponse createPatientIdentifierType(PatientIdentifierTypeCreateRequest request) {
        return new ValidatedCrudRequester<PatientIdentifierTypeResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER_TYPE,
                ResponseSpec.entityWasCreated()
        ).post(request);
    }

    public static ValidatableResponse hasPatientIdentifierType(String uuid) {
        return new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER_TYPE,
                ResponseSpec.requestReturnsOk())
                .get(uuid);
    }
}
