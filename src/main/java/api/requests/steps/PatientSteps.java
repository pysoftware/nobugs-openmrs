package api.requests.steps;

import api.models.PatientCreateNewRequest;
import api.models.PatientIdentifierTypeCreateRequest;
import api.models.PatientIdentifierTypeResponse;
import api.models.PatientResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;

public class PatientSteps {
    private static PatientResponse createPatient(PatientCreateNewRequest request, ResponseSpecification responseSpecification) {
        return new ValidatedCrudRequester<PatientResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT,
                responseSpecification
        ).post(request);
    }

    public static PatientResponse createPatient(PatientCreateNewRequest request) {
        return createPatient(request, ResponseSpec.entityWasCreatad());
    }

    public static PatientResponse createPatientFailed(PatientCreateNewRequest request) {
        return createPatient(request, ResponseSpec.requestReturnsForbiddenRequest());
    }

    public static PatientIdentifierTypeResponse createPatientIdentifierType(PatientIdentifierTypeCreateRequest request) {
        return new ValidatedCrudRequester<PatientIdentifierTypeResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER_TYPE,
                ResponseSpec.entityWasCreatad()
        ).post(request);
    }

    public static ValidatableResponse hasPatient(String uuid) {
        return new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT,
                ResponseSpec.requestReturnsOk())
                .get(uuid);
    }
}
