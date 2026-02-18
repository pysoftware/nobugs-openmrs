package api.person;

import api.BaseTest;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import common.annotations.PrepareData;
import common.extensions.Prepare;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

import static api.requests.steps.PersonSteps.getNotExistPerson;
import static api.specs.ResponseSpec.errorPersonNotExist;

public class DeletePersonTest extends BaseTest {
    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanDeletePerson() {

        String personUuid = SessionStorage.get(Prepare.PERSON, 1).getUuid();

        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsNoContent())
                .delete(personUuid);

        getNotExistPerson(personUuid, errorPersonNotExist);
    }

}
