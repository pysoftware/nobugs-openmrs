package api.requests.steps;

import api.models.*;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;

public class LocationSteps {
    public static LocationResponse createLocation(LocationCreateRequest request) {
        return new ValidatedCrudRequester<LocationResponse>(
                RequestSpec.adminSpec(),
                Endpoint.LOCATION,
                ResponseSpec.entityWasCreatad()
        ).post(request);
    }
}
