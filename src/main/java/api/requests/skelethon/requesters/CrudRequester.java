package api.requests.skelethon.requesters;


import api.models.BaseModel;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.HttpRequest;
import api.requests.skelethon.interfaces.CrudEndpointInterface;
import api.requests.skelethon.interfaces.GetAllEndpointInterface;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.function.Consumer;

import static io.restassured.RestAssured.given;

public class CrudRequester extends HttpRequest implements CrudEndpointInterface, GetAllEndpointInterface {

    public CrudRequester(RequestSpecification requestSpecification, Endpoint endpoint, ResponseSpecification responseSpecification) {
        super(requestSpecification, endpoint, responseSpecification);
    }

    @Override
    public ValidatableResponse post(BaseModel model) {
        var body = model == null ? "" : model;
        return given()
                .spec(requestSpecification)
                .body(body)
                .post(endpoint.getUrl())
                .then()
                .assertThat()
                .spec(responseSpecification);
    }

    private ValidatableResponse doGet(Consumer<RequestSpecification> specCustomizer, String path) {
        RequestSpecification spec = given().spec(requestSpecification);
        specCustomizer.accept(spec);

        return spec
                .get(path)
                .then()
                .assertThat()
                .spec(responseSpecification);
    }

    @Override
    public ValidatableResponse get(String uuid) {
        return doGet(spec -> spec.pathParams("uuid", uuid), endpoint.getUrl() + "/{uuid}");
    }

    @Override
    public ValidatableResponse get() {
        return doGet(spec -> {}, endpoint.getUrl());
    }

    @Override
    public ValidatableResponse update(BaseModel model, String uuid) {

        return given()
                .spec(requestSpecification)
                .body(model)
                .post(endpoint.getUrl() + "/" + uuid)
                .then()
                .assertThat()
                .spec(responseSpecification);
    }

    @Override
    public Object delete(String uuid) {
        return given()
                .spec(requestSpecification)
                .delete(endpoint.getUrl() + "/" + uuid)
                .then()
                .assertThat()
                .spec(responseSpecification);
    }

    @Override
    public ValidatableResponse getAll(Class<?> clazz) {
        return given()
                .spec(requestSpecification)
                .get(endpoint.getUrl())
                .then().assertThat()
                .spec(responseSpecification);
    }
}
