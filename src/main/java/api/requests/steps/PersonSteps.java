package api.requests.steps;

import api.generators.RandomModelGenerator;
import api.models.PersonAddressCreateRequest;
import api.models.PersonAddressResponse;
import api.models.PersonCreateRequest;
import api.models.PersonResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import common.storage.SessionStorage;

import java.util.List;
import java.util.Map;

public class PersonSteps {
    public static PersonResponse createPerson(PersonCreateRequest request) {
        PersonResponse response = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.entityWasCreatad()
        ).post(request);

        SessionStorage.add(response);

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

    public static List<PersonResponse> getAllPersons(){
        var params = Map.<String, Object>of("q", " ");
        return new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsOk()
        ).getAll(PersonResponse.class, params);
    }

    public static PersonAddressResponse createAddressPerson(PersonAddressCreateRequest request, String uuid) {
        PersonAddressResponse response = new ValidatedCrudRequester<PersonAddressResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON_ADDRES,
                ResponseSpec.entityWasCreatad()
        ).post(request, uuid);

        return response;
    }

    public static List<PersonAddressResponse> getAllPersonAddress(){
        var params = Map.<String, Object>of("q", " ");
        return new ValidatedCrudRequester<PersonAddressResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON_ADDRES ,
                ResponseSpec.requestReturnsOk()
        ).getAll(PersonAddressResponse.class, params);
    }

}
