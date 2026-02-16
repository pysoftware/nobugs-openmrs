package api.person;

import api.BaseTest;
import api.generators.RandomModelGenerator;
import api.models.*;
import api.models.comparison.ModelAssertions;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import common.annotations.PrepareData;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static api.requests.steps.PersonSteps.*;
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

    }
    @PrepareData(value = "person")
    @Test
    public void adminCanCreatePersonWithAddres() {

        String personUuid = SessionStorage.getPerson(1).getUuid();

        PersonAddressCreateRequest personAddressCreateRequest = RandomModelGenerator.generate(PersonAddressCreateRequest.class);
        PersonAddressResponse personAddressResponse = createAddressPerson(personAddressCreateRequest, personUuid);

        assertThat(getPerson(personUuid).getPreferredAddress().getAddress1()).isEqualTo(personAddressResponse.getAddress1());

        ModelAssertions.assertThatModels(personAddressCreateRequest, personAddressResponse).match();
    }

    @Test
    public void adminCanCreatePersonWithoutGender() {
        PersonCreateRequest personRequest = RandomModelGenerator.generate(PersonCreateRequest.class);
        personRequest.setGender(null);

        PersonResponse personResponse = createPerson(personRequest);
        String personUuid = personResponse.getUuid();

        assertThat(getPerson(personUuid)).isNotNull();

        ModelAssertions.assertThatModels(personRequest, personResponse).match();

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

    }
    @PrepareData(value = "person")
    @Test
    public void adminCanNotCreatePersonWithoutName() {
        int countPersonExpected = getAllPersons().size();

        PersonCreateRequest personRequest = RandomModelGenerator.generate(PersonCreateRequest.class);
        personRequest.setNames(null);

        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsBadRequest(errorPersonNamesIsNull))
                .post(personRequest);

        int countPersonActual = getAllPersons().size();

        softly.assertThat(countPersonActual).isEqualTo(countPersonExpected);
    }
}
