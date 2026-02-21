package api.person;

import api.BaseTest;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.steps.ErrorMessages;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

import static api.requests.steps.PersonSteps.*;
import static org.assertj.core.api.Assertions.assertThat;


public class DeletePersonTest extends BaseTest {
    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanDeletePerson() {
        String personUuid = SessionStorage.get(Prepare.PERSON, 1).getUuid();

        deletePerson(true, personUuid);

        getNotExistPerson(personUuid, ErrorMessages.OBJECT_DOES_NOT_EXIST.toString());
    }

    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanDeletePersonWithoutPurge() {
        String personUuid = SessionStorage.get(Prepare.PERSON, 1).getUuid();

        deletePerson(false, personUuid);

        assertThat(getPerson(personUuid)).isNotNull();
    }

}
