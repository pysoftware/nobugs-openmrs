package api.person;

import api.BaseTest;
import api.generators.RandomModelGenerator;
import api.models.PersonCreateRequest;
import api.models.PersonResponse;
import api.models.comparison.ModelAssertions;
import api.models.enums.Gender;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

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
/*        PersonResponse personResponse2 = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsOk()
        ).get(personResponse.getUuid());*/

        ModelAssertions.assertThatModels(user,personResponse).match();
    }

    @Test
    public void adminCanCreatePersonWithoutGender() {

        //создание объекта пользователя
        PersonCreateRequest user = RandomModelGenerator.generate(PersonCreateRequest.class);
        user.setGender(null);
        // создание пользователя
        PersonResponse personResponse = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.entityWasCreatad())
                .post(user);

        ModelAssertions.assertThatModels(user,personResponse).match();
    }

   /* @Test
    public void adminCanNotCreatePersonWithInvalidGender() {

        //создание объекта пользователя
        PersonCreateRequest user = RandomModelGenerator.generate(PersonCreateRequest.class);
        try {
            Field genderField = PersonCreateRequest.class.getDeclaredField("gender");
            genderField.setAccessible(true);
            genderField.set(user, "INVALID_GENDER");  // String вместо enum
        } catch (Exception e) {
            throw new RuntimeException("Не удалось установить невалидный gender", e);
        }
        // создание пользователя
        PersonResponse personResponse = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsBadRequest("Some required properties are missing: gender"))
                .post(user);

        ModelAssertions.assertThatModels(user,personResponse).match();
    }*/

    @Test
    public void adminCanNotCreatePersonWithoutName() {

        //создание объекта пользователя
        PersonCreateRequest user = RandomModelGenerator.generate(PersonCreateRequest.class);
        user.setNames(null);
        // создание пользователя
        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsBadRequest("[names on class org.openmrs.Person => Cannot invoke \"java.util.List.iterator()\" because \"personNames\" is null]"))
                .post(user);

    }

}
