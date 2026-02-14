package api.requests.steps;

import api.generators.RandomModelGenerator;
import api.models.PersonCreateRequest;
import api.models.PersonResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import common.storage.SessionStorage;

public class AdminSteps {
    public static PersonResponse createPerson(PersonCreateRequest request) {
        PersonResponse response = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.entityWasCreatad()
        ).post(request);

        SessionStorage.addPerson(response);

        return response;
    }

    public static PersonResponse createPerson() {
        PersonCreateRequest request = RandomModelGenerator.generate(PersonCreateRequest.class);
        return createPerson(request);
    }
    public static PersonResponse getPerson(String uuid) {
        return new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsOk()
        ).get(uuid);

    }


}
