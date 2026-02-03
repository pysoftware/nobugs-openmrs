package api;


import api.models.LoginResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("api")
@Tag("iteration-1")
public class LoginUserTest extends BaseTest {

    @Test
    public void adminCanLoginTest() {

        new ValidatedCrudRequester<LoginResponse>(
                RequestSpec.adminSpec(),
                Endpoint.LOGIN,
                ResponseSpec.requestReturnsOk())
                .get();
    }

  /*  @Test
    public void userCanGenerateAuthTokenTest() {
        // создание объекта пользователя
        CreateUserRequest user1 = AdminSteps.createUser();

        // создание объекта для логирования
        LoginRequest userLogin = LoginRequest.builder()
                .username(user1.getUsername())
                .password(user1.getPassword())
                .build();

        // получаем токен юзера
        new CrudRequester(
                RequestSpec.unauthSpec(),
                Endpoint.LOGIN,
                ResponseSpec.requestReturnsOk())
                .post(userLogin)
                .header("Authorization", Matchers.notNullValue());

    }*/
}
