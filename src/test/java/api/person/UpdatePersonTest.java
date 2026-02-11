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
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

public class UpdatePersonTest extends BaseTest {
    @PrepareData(value = "person")
    @Test
    public void adminCanCreatePersonWithCorrectData() {

        //создание объекта пользователя
        String uuidPerson = SessionStorage.getPerson(1).getUuid();
        PersonUpdateRequest personUpdate = RandomModelGenerator.generate(PersonUpdateRequest.class);
        // обновление пользователя
        PersonResponse personResponse = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsOk())
                .update(personUpdate, uuidPerson);

        ModelAssertions.assertThatModels(personUpdate, personResponse).match();
    }
}
