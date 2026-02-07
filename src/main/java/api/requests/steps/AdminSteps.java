package api.requests.steps;

import api.generators.RandomModelGenerator;
import api.models.PersonCreateRequest;
import api.models.PersonResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;

public class AdminSteps {
    public static PersonResponse createPerson(PersonCreateRequest request) {
        return new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.entityWasCreatad()   // предполагаю, что метод называется именно так
        ).post(request);
    }

    public static PersonResponse createPerson() {
        PersonCreateRequest request = RandomModelGenerator.generate(PersonCreateRequest.class);
        return createPerson(request);
    }



}
