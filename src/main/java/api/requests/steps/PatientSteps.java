package api.requests.steps;

import api.generators.RandomModelGenerator;
import api.models.*;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import common.storage.SessionStorage;
import io.restassured.response.ValidatableResponse;

import java.util.List;
import java.util.Map;

public class PatientSteps {
    public static PatientResponse createPatient() {
        PatientIdentifierTypeResponse patientIdentifierType = PatientIdentifierTypeSteps.createPatientIdentifierType();
        SessionStorage.add(patientIdentifierType);
        LocationResponse location = LocationSteps.createLocation();
        SessionStorage.add(location);
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
        return createPatient(patientRequest);
    }

    public static PatientResponse createPatient(PatientCreateRequest request) {
        PatientResponse patient = new ValidatedCrudRequester<PatientResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT,
                ResponseSpec.entityWasCreated()
        ).post(request);

        SessionStorage.add(patient);
        return patient;
    }

    public static void createPatientFailed(PatientCreateRequest request) {
        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT,
                ResponseSpec.requestReturnsBadRequest(ErrorMessages.IDENTIFIER_TYPE_IS_NULL.toString())
        ).post(request);
    }

    public static void deletePatient(boolean purge, String uuid) {
        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT,
                ResponseSpec.requestReturnsNoContent()
        ).delete(purge, uuid);
    }

    public static void deletePatientFailed(boolean purge, String uuid) {
        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT,
                ResponseSpec.requestReturnsNotFound(ErrorMessages.OBJECT_DOES_NOT_EXIST.toString())
        ).delete(purge, uuid);
    }

    public static ValidatableResponse hasPatient(String uuid) {
        return new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT,
                ResponseSpec.requestReturnsOk())
                .get(uuid);
    }

    public static List<PatientResponse> getPatientsByName(String name) {
        var params = Map.<String, Object>of("q", name);
        return new ValidatedCrudRequester<PatientResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT,
                ResponseSpec.requestReturnsOk()
        ).getAll(PatientResponse.class, params);
    }

    public static ValidatableResponse getAllVisitTypesRaw() {
        var params = Map.<String, Object>of(
                "limit", 1,
                "startIndex", 2,
                "v", "default"
        );

        return new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.VISITTYPE,
                ResponseSpec.requestReturnsOk()
        ).getAll(VisitTypeResults.class, params);
    }

    public static VisitTypeResponse getVisitType() {
        return PatientSteps.getAllVisitTypesRaw()
                .extract()
                .jsonPath()
                .getObject("results[0]", VisitTypeResponse.class);
    }

    public static VisitResponse createVisit(Visit request) {
        return new ValidatedCrudRequester<VisitResponse>(
                RequestSpec.adminSpec(),
                Endpoint.CREATEVISIT,
                ResponseSpec.entityWasCreated()
        ).post(request);
    }
}
