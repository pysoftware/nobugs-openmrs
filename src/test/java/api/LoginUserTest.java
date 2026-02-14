package api;


import api.models.LoginResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("api")
public class LoginUserTest extends BaseTest {

    @Test
    public void adminCanLoginTest() {

        new ValidatedCrudRequester<LoginResponse>(
                RequestSpec.adminSpec(),
                Endpoint.LOGIN,
                ResponseSpec.requestReturnsOk())
                .get();
    }

}
