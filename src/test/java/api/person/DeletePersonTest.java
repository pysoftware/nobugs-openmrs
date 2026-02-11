package api.person;

import api.BaseTest;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import common.annotations.PrepareData;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;

public class DeletePersonTest extends BaseTest {
    @PrepareData(value = "person")
    @Test
    public void adminCanNotCreatePersonWithoutName() {

        String uuidPerson = SessionStorage.getPerson(1).getUuid();

        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsNoContent())
                .delete(uuidPerson);
    }

}
