package api.person;

import api.BaseTest;
import api.database.dao.PersonUuidDao;
import api.database.dao.comparison.DaoAndModelAssertions;
import api.database.dao.comparison.DaoComparisonConfig;
import api.database.dao.comparison.DaoModelComparator;
import api.generators.RandomModelGenerator;
import api.models.PersonCreateRequest;
import api.models.PersonName;
import api.models.PersonResponse;
import api.models.comparison.ModelAssertions;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.requests.steps.DataBaseSteps;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.Comparator;
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

        PersonUuidDao personUuidDao1 = DataBaseSteps.getPersonByUuid(personResponse1.getUuid());
        PersonUuidDao personUuidDao2 = DataBaseSteps.getPersonByUuid(personResponse2.getUuid());

        //personResponse1.setUuid("23433");

        DaoAndModelAssertions.assertThat(personUuidDao1, personResponse1).match();
        DaoAndModelAssertions.assertThat(personUuidDao2, personResponse2).match();

//        softly.assertThat(personUuidDao1.getUuid()).isEqualTo(personResponse1.getUuid());
//        softly.assertThat(personUuidDao2.getUuid()).isEqualTo(personResponse2.getUuid());
//        softly.assertThat(personUuidDao1.getUuid()).isNotEqualTo(personUuidDao2.getUuid());

    }

    @Test
    public void adminCanNotCreatePersonWithoutName() {

        PersonCreateRequest user = RandomModelGenerator.generate(PersonCreateRequest.class);
        user.setNames(null);

        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsBadRequest(errorPersonNamesIsNull))
                .post(user).extract().as(PersonResponse.class);

    }
}
