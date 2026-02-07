package api.person;

import api.BaseTest;
import api.generators.RandomModelGenerator;
import api.models.PersonCreateRequest;
import api.models.PersonName;
import api.models.PersonResponse;
import api.models.comparison.ModelAssertions;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import org.junit.jupiter.api.Test;

import java.util.List;

import static api.requests.steps.AdminSteps.createPerson;
import static api.specs.ResponseSpec.errorPersonNamesIsNull;

public class CreatePersonTest extends BaseTest {
    @Test
    public void adminCanCreatePersonWithCorrectData() {

        //создание объекта пользователя
        PersonCreateRequest user = RandomModelGenerator.generate(PersonCreateRequest.class);

        // создание пользователя
        PersonResponse personResponse = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.entityWasCreatad())
                .post(user);

        ModelAssertions.assertThatModels(user, personResponse).match();
    }

    @Test
    public void adminCanCreatePersonWithoutGender() {

        PersonCreateRequest user = RandomModelGenerator.generate(PersonCreateRequest.class);
        user.setGender(null);

        PersonResponse personResponse = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.entityWasCreatad())
                .post(user);

        ModelAssertions.assertThatModels(user, personResponse).match();
    }

    @Test
    public void adminCanCreatePersonWithSameName() {

        PersonCreateRequest user = RandomModelGenerator.generate(PersonCreateRequest.class);
        PersonResponse personResponse1 = createPerson(user);
        PersonName sameName = user.getNames().get(0);

        PersonCreateRequest user2 = RandomModelGenerator.generate(PersonCreateRequest.class);
        user2.setNames(List.of(sameName));
        PersonResponse personResponse2 = createPerson(user2);

        ModelAssertions.assertThatModels(user2, personResponse2).match();
    }

    @Test
    public void adminCanNotCreatePersonWithoutName() {

        PersonCreateRequest user = RandomModelGenerator.generate(PersonCreateRequest.class);
        user.setNames(null);

        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsBadRequest(errorPersonNamesIsNull))
                .post(user);

    }

}
