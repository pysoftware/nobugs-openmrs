package api.requests.steps;

import api.models.PatientCreateNewRequest;
import api.models.PatientIdentifierTypeCreateRequest;
import api.models.PatientIdentifierTypeResponse;
import api.models.PatientResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;

public class PatientSteps {
    public static PatientResponse createPatient(PatientCreateNewRequest request) {
        return new ValidatedCrudRequester<PatientResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT,
                ResponseSpec.entityWasCreatad()
        ).post(request);
    }

    public static PatientIdentifierTypeResponse createPatientIdentifierType(PatientIdentifierTypeCreateRequest request) {
        return new ValidatedCrudRequester<PatientIdentifierTypeResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER_TYPE,
                ResponseSpec.entityWasCreatad()
        ).post(request);
    }
}
