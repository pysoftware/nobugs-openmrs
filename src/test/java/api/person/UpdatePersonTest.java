package api.person;

import api.BaseTest;
import api.generators.RandomModelGenerator;
import api.models.*;
import api.models.comparison.ModelAssertions;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static api.requests.steps.PersonSteps.*;

public class UpdatePersonTest extends BaseTest {
    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanUpdatePersonBirthdate() {
        PersonResponse personResponse = SessionStorage.get(Prepare.PERSON, 1);
        String personUuid = personResponse.getUuid();
        OffsetDateTime personBirthdate = personResponse.getBirthdate();

        PersonUpdateRequest personUpdate = RandomModelGenerator.generate(PersonUpdateRequest.class);

        PersonResponse personResponseUpdate = updatePerson(personUpdate, personUuid);

        softly.assertThat(getPerson(personUuid).getBirthdate()).isNotEqualTo(personBirthdate);

        ModelAssertions.assertThatModels(personUpdate, personResponseUpdate).match();
    }

    @PrepareData(Prepare.PERSON)
    @Disabled
    @Test
    public void adminCanUpdatePersonName() {
        PersonResponse personResponse = SessionStorage.get(Prepare.PERSON, 1);
        String personUuid = personResponse.getUuid();
        String originalDisplayName = personResponse.getPreferredName().getDisplay();

        PersonNameUpdateRequest nameUpdate = RandomModelGenerator.generate(PersonNameUpdateRequest.class);
        nameUpdate.setPreferred(true);

        PersonNameResponse createdName = updateNamePerson(nameUpdate, personUuid);

        PersonResponse updatedPerson = getPerson(personUuid);

        softly.assertThat(updatedPerson.getPreferredName().getDisplay())
                .isNotEqualTo(originalDisplayName);

        ModelAssertions.assertThatModels(nameUpdate, createdName).match();
    }

    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanUpdatePersonAddress() {
        PersonResponse personResponse = SessionStorage.get(Prepare.PERSON, 1);
        String personUuid = personResponse.getUuid();
        String originalDisplayAddress = personResponse.getPreferredAddress().getDisplay();

        PersonAddressCreateRequest addressUpdate = RandomModelGenerator.generate(PersonAddressCreateRequest.class);
        addressUpdate.setPreferred(true);
        PersonAddressResponse createdAddress = createAddressPerson(addressUpdate, personUuid);

        PersonResponse updatedPerson = getPerson(personUuid);

        softly.assertThat(updatedPerson.getPreferredAddress().getDisplay())
                .isNotEqualTo(originalDisplayAddress);

        ModelAssertions.assertThatModels(addressUpdate, createdAddress).match();
    }
}
