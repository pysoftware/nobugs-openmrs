package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationCreateRequest extends BaseModel {
    private String name;
    private String description;
    private String address1;
    private String cityVillage;
    private String stateProvince;
    private String country;
    private String postalCode;
    private String countyDistrict;
    private List<String> tags;
    private List<String> childLocations;
    private List<LocationAttribute> attributes;
}
