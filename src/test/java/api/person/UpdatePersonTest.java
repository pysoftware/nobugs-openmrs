package api.person;

import api.BaseTest;
import api.generators.RandomModelGenerator;
import api.models.PersonResponse;
import api.models.PersonUpdateRequest;
import api.models.comparison.ModelAssertions;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

public class UpdatePersonTest extends BaseTest {
    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanCreatePersonWithCorrectData() {

        String uuidPerson = SessionStorage.get(Prepare.PERSON, 1).getUuid();
        PersonUpdateRequest personUpdate = RandomModelGenerator.generate(PersonUpdateRequest.class);

        PersonResponse personResponse = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsOk())
                .update(personUpdate, uuidPerson);

        ModelAssertions.assertThatModels(personUpdate, personResponse).match();
    }
}
