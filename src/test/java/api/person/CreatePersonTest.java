package api.person;

import api.BaseTest;
import api.generators.RandomModelGenerator;
import api.models.PersonCreateRequest;
import api.models.PersonResponse;
import api.models.comparison.ModelAssertions;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import org.junit.jupiter.api.Test;

public class CreatePersonTest extends BaseTest {
    @Test
    public void adminCanCreatePersonWithCorrectData() {

        //создание объекта пользователя
        PersonCreateRequest user1 = RandomModelGenerator.generate(PersonCreateRequest.class);

        // создание пользователя
        PersonResponse personResponse = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.entityWasCreatad())
                .post(user1);
/*        PersonResponse personResponse2 = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsOk()
        ).get(personResponse.getUuid());*/

        ModelAssertions.assertThatModels(user1,personResponse).match();
    }
}
