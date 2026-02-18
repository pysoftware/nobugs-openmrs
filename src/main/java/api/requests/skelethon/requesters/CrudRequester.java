package api.requests.skelethon.requesters;


import api.models.BaseModel;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.HttpRequest;
import api.requests.skelethon.interfaces.CrudEndpointInterface;
import api.requests.skelethon.interfaces.GetAllEndpointInterface;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;
import java.util.function.Consumer;

import static io.restassured.RestAssured.given;

public class CrudRequester extends HttpRequest implements CrudEndpointInterface, GetAllEndpointInterface {

    public CrudRequester(RequestSpecification requestSpecification, Endpoint endpoint, ResponseSpecification responseSpecification) {
        super(requestSpecification, endpoint, responseSpecification);
    }

    private ValidatableResponse doPost(Consumer<RequestSpecification> specCustomizer, String path, BaseModel model) {
        RequestSpecification spec = given().spec(requestSpecification);
        if (model != null) {
            spec.body(model);
        } else {
            throw new IllegalArgumentException("Body model is required for POST");
        }
        specCustomizer.accept(spec);

        return spec
                .post(path)
                .then()
                .assertThat()
                .spec(responseSpecification);
    }

    @Override
    public ValidatableResponse post(BaseModel model, String uuid) {
        return doPost(spec -> spec.pathParams("uuid", uuid), endpoint.getUrl() + "/{uuid}/address", model);
    }

    @Override
    public ValidatableResponse post(BaseModel model) {
        return doPost(spec -> {}, endpoint.getUrl(), model);
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
                .delete(endpoint.getUrl() + "/" + uuid + "?purge=true")
                .then()
                .assertThat()
                .spec(responseSpecification);
    }

    @Override
    public ValidatableResponse getAll(Class<?> clazz) {
        return getAll(clazz, null);
    }

    @Override
    public ValidatableResponse getAll(Class<?> clazz, Map<String, ?> queryParams) {
        RequestSpecification spec = given().spec(requestSpecification);

        if (queryParams != null) {
            spec.queryParams(queryParams);
        }

        return spec
                .get(endpoint.getUrl())
                .then()
                .assertThat()
                .spec(responseSpecification);
    }
}
