package api.requests.steps;

import api.models.PatientIdentifierCreateRequest;
import api.models.PatientIdentifierResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;

import java.util.List;

public class PatientIdentifierSteps {
    public static PatientIdentifierResponse createPatientIdentifier(PatientIdentifierCreateRequest request, String uuid) {
        return new ValidatedCrudRequester<PatientIdentifierResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER,
                ResponseSpec.entityWasCreated()
        ).post(request, uuid);
    }

    public static void createPatientIdentifierFailed(PatientIdentifierCreateRequest request, String uuid) {
        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER,
                ResponseSpec.requestReturnsBadRequest(ErrorMessages.IDENTIFIER_TYPE_IS_NULL)
        ).post(request, uuid);
    }

    public static List<PatientIdentifierResponse> getPatientIdentifiers(String uuid) {
        return new ValidatedCrudRequester<PatientIdentifierResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER,
                ResponseSpec.requestReturnsOk())
                .getAll(PatientIdentifierResponse.class, uuid);
    }
}
