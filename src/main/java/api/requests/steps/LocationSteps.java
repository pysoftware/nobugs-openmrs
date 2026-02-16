package api.requests.steps;

import api.generators.RandomModelGenerator;
import api.models.*;
import api.models.comparison.ModelAssertions;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;

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

        LocationResponse location = createLocation(locationCreateRequest);

        ModelAssertions.assertThatModels(locationCreateRequest, location).match();
        return location;
    }

    public static LocationResponse createLocation(LocationCreateRequest request) {
        return new ValidatedCrudRequester<LocationResponse>(
                RequestSpec.adminSpec(),
                Endpoint.LOCATION,
                ResponseSpec.entityWasCreatad()
        ).post(request);
    }
}
