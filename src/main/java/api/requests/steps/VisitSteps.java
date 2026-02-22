package api.requests.steps;

import api.models.*;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.specs.RequestSpec;
import api.specs.ResponseSpec;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

public class VisitSteps {

    public static ValidatableResponse getAllVisitTypesRaw() {
        var params = Map.<String, Object>of(
                "limit", 1,
                "startIndex", 2,
                "v", "default"
        );

        return new CrudRequester(
                RequestSpec.adminSpec(),
                Endpoint.VISITTYPE,
                ResponseSpec.requestReturnsOk()
        ).getAll(VisitTypeResults.class, params);
    }

    public static VisitTypeResponse getVisitType() {
        return VisitSteps.getAllVisitTypesRaw()
                .extract()
                .jsonPath()
                .getObject("results[0]", VisitTypeResponse.class);
    }

    public static VisitResponse createVisit(Visit request) {
        return new ValidatedCrudRequester<VisitResponse>(
                RequestSpec.adminSpec(),
                Endpoint.VISIT,
                ResponseSpec.entityWasCreated()
        ).post(request);
    }

    public static VisitTypeResponse createVisitType(VisitTypeCreateRequest request) {
        return new ValidatedCrudRequester<VisitTypeResponse> (
                RequestSpec.adminSpec(),
                Endpoint.VISITTYPE,
                ResponseSpec.entityWasCreated()
        ).post(request);
    }

    public static VisitTypeResponse updateVisitType(VisitTypeCreateRequest request, String uuid) {
        return new ValidatedCrudRequester<VisitTypeResponse>(
                RequestSpec.adminSpec(),
                Endpoint.VISITTYPE,
                ResponseSpec.requestReturnsOk()
        ).post(request, uuid);
    }
}
