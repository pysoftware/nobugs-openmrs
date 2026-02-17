package api;

import api.database.dao.LocationDao;
import api.database.dao.comparison.DaoAndModelAssertions;
import api.generators.RandomModelGenerator;
import api.models.LocationCreateRequest;
import api.models.LocationResponse;
import api.models.comparison.ModelAssertions;
import api.requests.steps.DataBaseSteps;
import api.requests.steps.LocationSteps;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LocationTest extends BaseTest {
    @Test
    public void createLocation() {
        LocationCreateRequest locationCreateRequest =
                RandomModelGenerator.generate(LocationCreateRequest.class,
                        fields -> {
                            fields.setTags(List.of());
                            fields.setChildLocations(List.of());
                            fields.setAttributes(List.of());
                        });

        LocationResponse location = LocationSteps.createLocation(locationCreateRequest);

        ModelAssertions.assertThatModels(locationCreateRequest, location).match();

        assertThat(LocationSteps.hasLocation(location.getUuid())).isNotNull();

        LocationDao locationDao = DataBaseSteps.getLocationByUuid(location.getUuid());
        DaoAndModelAssertions.assertThat(location, locationDao).match();
    }
}
