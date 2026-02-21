package api.requests.steps;

import api.generators.RandomModelGenerator;
import api.models.CreateTagRequest;
import api.models.CreateTagResponse;
import api.models.LocationCreateRequest;
import api.models.LocationResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import com.github.javafaker.Faker;
import io.restassured.response.ValidatableResponse;

import java.util.List;

public class LocationSteps {
    public static LocationResponse createLocation() {
        LocationCreateRequest locationCreateRequest =
                RandomModelGenerator.generate(LocationCreateRequest.class,
                        fields -> {
                            fields.setTags(List.of());
                            fields.setChildLocations(List.of());
                            fields.setAttributes(List.of());
                        });

        return createLocation(locationCreateRequest);
    }

    public static LocationResponse createLocation(LocationCreateRequest request) {
        return new ValidatedCrudRequester<LocationResponse>(
                RequestSpec.adminSpec(),
                Endpoint.LOCATION,
                ResponseSpec.entityWasCreated()
        ).post(request);
    }

    public static ValidatableResponse hasLocation(String uuid) {
        return new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.LOCATION,
                ResponseSpec.requestReturnsOk())
                .get(uuid);
    }

    public static CreateTagResponse createTag() {
        Faker faker = new Faker();
        CreateTagRequest createTagRequest = CreateTagRequest.builder().name(faker.name().firstName()+" Hospital").description("Description").build();

        return new ValidatedCrudRequester<CreateTagResponse>(
                RequestSpec.adminSpec(),
                Endpoint.CREATETAG,
                ResponseSpec.entityWasCreated()
        ).post(createTagRequest);
    }
}
