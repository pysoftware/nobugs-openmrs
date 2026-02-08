package api.person;

import api.BaseTest;
import api.models.PersonResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import org.junit.jupiter.api.Test;

import static api.requests.steps.AdminSteps.createPerson;

public class DeletePersonTest extends BaseTest {

    @Test
    public void adminCanNotCreatePersonWithoutName() {

        PersonResponse user = createPerson();
        String uuid = user.getUuid();

        new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.PERSON,
                ResponseSpec.requestReturnsNoContent())
                .delete(uuid);

    }

}
