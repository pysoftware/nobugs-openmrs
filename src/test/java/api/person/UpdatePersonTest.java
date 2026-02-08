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
import org.junit.jupiter.api.Test;

import static api.requests.steps.AdminSteps.createPerson;

public class UpdatePersonTest extends BaseTest {
    @Test
    public void adminCanCreatePersonWithCorrectData() {

        //создание объекта пользователя
        PersonResponse user = createPerson();
        String uuid = user.getUuid();
        PersonUpdateRequest userUpdate = RandomModelGenerator.generate(PersonUpdateRequest.class);
        // обновление пользователя
        PersonResponse personResponse = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsOk())
                .update(userUpdate, uuid);

        ModelAssertions.assertThatModels(userUpdate, personResponse).match();
    }
}
