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
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

import static api.requests.steps.PersonSteps.getPerson;

public class UpdatePersonTest extends BaseTest {
    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanCreatePersonWithCorrectData() {
        PersonResponse personResponse = SessionStorage.get(Prepare.PERSON, 1);
        String personUuid = personResponse.getUuid();
        String personDisplayName = personResponse.getPreferredName().getDisplay();
        String personDisplayAddress = personResponse.getPreferredAddress().getDisplay();

        PersonUpdateRequest personUpdate = RandomModelGenerator.generate(PersonUpdateRequest.class);

        PersonResponse personResponseUpdate = new ValidatedCrudRequester<PersonResponse>(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsOk())
                .update(personUpdate, personUuid);

        softly.assertThat(getPerson(personUuid).getPreferredAddress().getDisplay()).isNotEqualTo(personDisplayName);
        softly.assertThat(getPerson(personUuid).getPreferredName().getDisplay()).isNotEqualTo(personDisplayAddress);

        ModelAssertions.assertThatModels(personUpdate, personResponseUpdate).match();
    }
}
