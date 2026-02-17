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

public class DeletePersonTest extends BaseTest {
    @PrepareData(Prepare.PERSON)
    @Test
    public void adminCanNotCreatePersonWithoutName() {

        String uuidPerson = SessionStorage.get(Prepare.PERSON, 1).getUuid();

        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsNoContent())
                .delete(uuidPerson);
    }

}
