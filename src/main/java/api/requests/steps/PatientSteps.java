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
import io.restassured.specification.ResponseSpecification;

public class PatientSteps {
    private static PatientResponse createPatient(PatientCreateNewRequest request, ResponseSpecification responseSpecification) {
        return new ValidatedCrudRequester<PatientResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT,
                responseSpecification
        ).post(request);
    }

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
                            }
                            Person person = fields.getPerson();
                            person.setDead(false);
                            person.setCauseOfDeath(null);
                            person.setDeathDate(null);
                        });
        return createPatient(patientRequest);
    }

    public static PatientResponse createPatient(PatientCreateNewRequest request) {
        PatientResponse patient = createPatient(request, ResponseSpec.entityWasCreatad());
        SessionStorage.add(patient);
        return patient;
    }

    public static PatientResponse createPatientFailed(PatientCreateNewRequest request) {
        return createPatient(request, ResponseSpec.requestReturnsForbiddenRequest());
    }

    public static ValidatableResponse hasPatient(String uuid) {
        return new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PATIENT,
                ResponseSpec.requestReturnsOk())
                .get(uuid);
    }
}
