package api.person;

import api.BaseTest;
import api.database.dao.PersonUuidDao;
import api.database.dao.comparison.DaoAndModelAssertions;
import api.generators.RandomModelGenerator;
import api.models.PersonCreateRequest;
import api.models.PersonName;
import api.models.PersonResponse;
import api.models.comparison.ModelAssertions;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.steps.DataBaseSteps;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import common.annotations.PrepareData;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static api.requests.steps.AdminSteps.createPerson;
import static api.requests.steps.AdminSteps.getPerson;
import static api.specs.ResponseSpec.errorPersonNamesIsNull;
import static org.assertj.core.api.Assertions.assertThat;

public class CreatePersonTest extends BaseTest {

    @Test
    public void adminCanCreatePersonWithCorrectData() {
        PersonCreateRequest personRequest = RandomModelGenerator.generate(PersonCreateRequest.class);

        PersonResponse personResponse = createPerson(personRequest);
        String personUuid = personResponse.getUuid();

        assertThat(getPerson(personUuid)).isNotNull();

        ModelAssertions.assertThatModels(personRequest, personResponse).match();

        PersonUuidDao personUuidDao = DataBaseSteps.getPersonByUuid(personUuid);
        DaoAndModelAssertions.assertThat(personUuidDao, personResponse).match();

    }

    @Test
    public void adminCanCreatePersonWithoutGender() {
        PersonCreateRequest personRequest = RandomModelGenerator.generate(PersonCreateRequest.class);
        personRequest.setGender(null);

        PersonResponse personResponse = createPerson(personRequest);
        String personUuid = personResponse.getUuid();

        assertThat(getPerson(personUuid)).isNotNull();

        ModelAssertions.assertThatModels(personRequest, personResponse).match();

        PersonUuidDao personUuidDao = DataBaseSteps.getPersonByUuid(personUuid);
        DaoAndModelAssertions.assertThat(personUuidDao, personResponse).match();
    }

    @PrepareData(value = "person")
    @Test
    public void adminCanCreatePersonWithSameName() {
        PersonResponse personResponse1 = SessionStorage.getPerson(1);
        String given = personResponse1.getPreferredName().getGivenName();
        String family = personResponse1.getPreferredName().getFamilyName();

        PersonCreateRequest personRequest2 = RandomModelGenerator.generate(PersonCreateRequest.class);
        PersonName newName = PersonName.builder()
                .givenName(given)
                .familyName(family)
                .build();

        personRequest2.setNames(List.of(newName));
        PersonResponse personResponse2 = createPerson(personRequest2);
        assertThat(getPerson(personResponse2.getUuid())).isNotNull();
        ModelAssertions.assertThatModels(personRequest2, personResponse2).match();

        PersonUuidDao personUuidDao1 = DataBaseSteps.getPersonByUuid(personResponse1.getUuid());
        PersonUuidDao personUuidDao2 = DataBaseSteps.getPersonByUuid(personResponse2.getUuid());

        DaoAndModelAssertions.assertThat(personUuidDao1, personResponse1).match();
        DaoAndModelAssertions.assertThat(personUuidDao2, personResponse2).match();

    }

    @Test
    public void adminCanNotCreatePersonWithoutName() {

        PersonCreateRequest personRequest = RandomModelGenerator.generate(PersonCreateRequest.class);
        personRequest.setNames(null);

        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsBadRequest(errorPersonNamesIsNull))
                .post(personRequest).extract().as(PersonResponse.class);

    }
}
