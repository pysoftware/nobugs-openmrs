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
public class LocationResponse extends BaseModel {
    private String uuid;
    private String display;
    private String name;
    private String description;
    private String address1;
    private String address2;
    private String cityVillage;
    private String stateProvince;
    private String country;
    private String postalCode;
    private String latitude;
    private String longitude;
    private String countyDistrict;
    private String address3;
    private String address4;
    private String address5;
    private String address6;
    private List<LocationTag> tags;
    private Object parentLocation;
    private List<LocationTag> childLocations;
    private Boolean retired;
    private List<Object> attributes;
    private String address7;
    private String address8;
    private String address9;
    private String address10;
    private String address11;
    private String address12;
    private String address13;
    private String address14;
    private String address15;
    private List<Link> links;
    private String resourceVersion;
}
