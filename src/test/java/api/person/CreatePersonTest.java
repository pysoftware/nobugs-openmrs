package api.person;

import api.BaseTest;
import api.generators.RandomModelGenerator;
import api.models.*;
import api.models.comparison.ModelAssertions;
import api.requests.steps.ErrorMessages;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static api.requests.steps.PersonSteps.*;
import static api.utils.StringUtils.parseDisplay;
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

    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanCreatePersonAddress() {

        String personUuid = SessionStorage.get(Prepare.PERSON, 1).getUuid();

        PersonAddressCreateRequest personAddressCreateRequest = RandomModelGenerator.generate(PersonAddressCreateRequest.class,
                fields -> fields.setPreferred(true));
        PersonAddressResponse personAddressResponse = createAddressPerson(personAddressCreateRequest, personUuid);

        assertThat(getPerson(personUuid).getPreferredAddress().getDisplay()).isEqualTo(personAddressResponse.getAddress1());

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

    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanCreatePersonWithSameName() {
        PersonResponse personResponse1 = SessionStorage.get(Prepare.PERSON, 1);

        String[] names = parseDisplay(personResponse1.getPreferredName().getDisplay());

        PersonCreateRequest request = RandomModelGenerator.generate(PersonCreateRequest.class,
                fields -> {
                    fields.setNames(List.of(
                            PersonName.builder()
                                    .givenName(names[0])
                                    .familyName(names[1])
                                    .build()
                    ));

                });
        PersonResponse personResponse2 = createPerson(request);
        assertThat(getPerson(personResponse2.getUuid())).isNotNull();
        ModelAssertions.assertThatModels(request, personResponse2).match();

    }

    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanNotCreatePersonWithoutName() {
        int countPersonExpected = getAllPersons().size();

        PersonCreateRequest personRequest = RandomModelGenerator.generate(PersonCreateRequest.class);
        personRequest.setNames(null);

        createPersonError(personRequest, ErrorMessages.PERSON_NAME_IS_NULL.toString());

        int countPersonActual = getAllPersons().size();

        softly.assertThat(countPersonActual).isEqualTo(countPersonExpected);
    }

    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanNotCreatePersonWithoutAddress() {
        int countPersonExpected = getAllPersons().size();

        PersonCreateRequest personRequest = RandomModelGenerator.generate(PersonCreateRequest.class);
        personRequest.setAddresses(null);

        createPersonError(personRequest, ErrorMessages.PERSON_ADDRESS_IS_NULL.toString());

        int countPersonActual = getAllPersons().size();

        softly.assertThat(countPersonActual).isEqualTo(countPersonExpected);
    }
}
