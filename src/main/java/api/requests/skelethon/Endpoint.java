package api.requests.skelethon;

import api.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Endpoint {

    LOGIN(
            "/session",
            LoginRequest.class,
            LoginResponse.class
    ),

    PERSON(
            "/person",
            PersonCreateRequest.class,
            PersonResponse.class
    );

    private final String url;
    private final Class<? extends BaseModel> requestModel;
    private final Class<? extends BaseModel> responseModel;
}
