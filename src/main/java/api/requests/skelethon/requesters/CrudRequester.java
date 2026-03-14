package api.requests.skelethon.requesters;


import api.models.BaseModel;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.HttpRequest;
import api.requests.skelethon.interfaces.CrudEndpointInterface;
import api.requests.skelethon.interfaces.GetAllEndpointInterface;
import common.storage.SessionStorage;
import io.restassured.http.Method;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import static io.restassured.RestAssured.given;

public class CrudRequester extends HttpRequest implements CrudEndpointInterface, GetAllEndpointInterface {
    public record RequestParams(String path, Map<String, String> pathParams) {
    }

    public CrudRequester(RequestSpecification requestSpecification, Endpoint endpoint, ResponseSpecification responseSpecification) {
        super(requestSpecification, endpoint, responseSpecification);
    }

    // Post
    private ValidatableResponse post(Consumer<RequestSpecification> specCustomizer, String path, BaseModel model) {
        RequestSpecification spec = given().spec(requestSpecification);
        if (model != null) {
            spec.body(model);
        } else {
            throw new IllegalArgumentException("Body model is required for POST");
        }
        specCustomizer.accept(spec);

        return execute(Method.POST, spec, path);
    }

    @Override
    public ValidatableResponse post(BaseModel model, String... uuids) {
        RequestParams params = buildParams(uuids);
        return post(spec -> spec.pathParams(params.pathParams()), params.path(), model);
    }

    @Override
    public ValidatableResponse post(BaseModel model) {
        return post(spec -> {
        }, endpoint.getUrl(), model);
    }

    // Get
    private ValidatableResponse get(Consumer<RequestSpecification> specCustomizer, String path) {
        RequestSpecification spec = given().spec(requestSpecification);
        specCustomizer.accept(spec);

        return execute(Method.GET, spec, path);
    }

    @Override
    public ValidatableResponse get(String... uuids) {
        RequestParams params = buildParams(uuids);
        return get(spec -> spec.pathParams(params.pathParams()), params.path());
    }

    @Override
    public ValidatableResponse get() {
        return get(spec -> {
        }, endpoint.getUrl());
    }

    // Update
    @Override
    public ValidatableResponse update(BaseModel model, String uuid) {
        return post(model, uuid);
    }

    // Delete
    @Override
    public Object delete(String... uuid) {
        return delete(false, uuid);
    }

    @Override
    public Object delete(boolean purge, String... uuid) {
        SessionStorage.delete(uuid[0]);

        RequestSpecification spec = given().spec(requestSpecification);
        if (purge) spec.queryParams(Map.<String, Object>of("purge", purge));

        RequestParams params = buildParams(uuid);
        spec.pathParams(params.pathParams());
        return execute(Method.DELETE, spec, params.path());
    }

    // Get All
    @Override
    public ValidatableResponse getAll(Class<?> clazz) {
        return getAll(clazz, (Map<String, ?>) null);
    }

    @Override
    public ValidatableResponse getAll(Class<?> clazz, Map<String, ?> queryParams) {
        RequestSpecification spec = given().spec(requestSpecification);

        if (queryParams != null) {
            spec.queryParams(queryParams);
        }

        return execute(Method.GET, spec, endpoint.getUrl());
    }

    @Override
    public ValidatableResponse getAll(Class<?> clazz, String... uuids) {
        RequestSpecification spec = given().spec(requestSpecification);

        RequestParams params = buildParams(uuids);
        spec.pathParams(params.pathParams());

        return execute(Method.GET, spec, params.path());
    }

    // формирование данных
    private RequestParams buildParams(String... uuids) {
        Map<String, String> params = new LinkedHashMap<>();
        String path = endpoint.getUrl();

        for (int i = 0; i < uuids.length; i++) {
            String uuidKey = i == 0 ? "uuid" : "uuid" + (i + 1);
            params.put(uuidKey, uuids[i]);
            path = path.contains(uuidKey) ? path : path + "/{" + uuidKey + "}";
        }

        return new RequestParams(path, params);
    }

    private ValidatableResponse execute(Method method, RequestSpecification spec, String path) {
        return spec
                .request(method, path)
                .then()
                .assertThat()
                .spec(responseSpecification);
    }
}
