package api.requests.steps;

import api.models.PatientIdentifierCreateRequest;
import api.models.PatientIdentifierResponse;
import api.models.PatientIdentifierUpdateRequest;
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
                ResponseSpec.requestReturnsBadRequest(ErrorMessages.IDENTIFIER_TYPE_IS_NULL.toString())
        ).post(request, uuid);
    }

    public static PatientIdentifierResponse updatePatientIdentifier(PatientIdentifierUpdateRequest request, String... uuids) {
        return new ValidatedCrudRequester<PatientIdentifierResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER,
                ResponseSpec.requestReturnsOk()
        ).post(request, uuids);
    }

    public static void updatePatientIdentifierFailed(PatientIdentifierUpdateRequest request, String... uuids) {
        String identifier = request.getIdentifier();
        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER,
                ResponseSpec.requestReturnsInternalServerError(ErrorMessages.FAILED_TO_VALIDATE.toString(identifier, identifier))
        ).post(request, uuids);
    }

    public static void updateNoExistentPatientIdentifierFailed(PatientIdentifierUpdateRequest request, String... uuids) {
        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER,
                ResponseSpec.requestReturnsNotFound(ErrorMessages.OBJECT_DOES_NOT_EXIST.toString())
        ).post(request, uuids);
    }

    public static void deletePatientIdentifier(boolean purge, String patientUuid, String patientIdentifierUuid) {
        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER,
                ResponseSpec.requestReturnsNoContent()
        ).delete(purge, patientUuid, patientIdentifierUuid);
    }

    public static void deletePatientIdentifierFailed(boolean purge, String patientUuid, String patientIdentifierUuid) {
        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER,
                ResponseSpec.requestReturnsNotFound(ErrorMessages.OBJECT_DOES_NOT_EXIST.toString())
        ).delete(purge, patientUuid, patientIdentifierUuid);
    }

    public static PatientIdentifierResponse getPatientIdentifier(String patientUuid, String identifierUuid) {
        return new ValidatedCrudRequester<PatientIdentifierResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER,
                ResponseSpec.requestReturnsOk())
                .get(patientUuid, identifierUuid);
    }

    public static void getPatientIdentifierThatDoesNotExist(String patientUuid, String identifierUuid) {
        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER,
                ResponseSpec.requestReturnsNotFound(ErrorMessages.OBJECT_DOES_NOT_EXIST.toString()))
                .get(patientUuid, identifierUuid);
    }

    public static List<PatientIdentifierResponse> getPatientIdentifiers(String patientUuid) {
        return new ValidatedCrudRequester<PatientIdentifierResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT_IDENTIFIER,
                ResponseSpec.requestReturnsOk())
                .getAll(PatientIdentifierResponse.class, patientUuid);
    }
}
