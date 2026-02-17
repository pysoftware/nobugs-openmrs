package api.database.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDao {
    private String uuid;
    private String name;
    private String description;
    private String address1;
    private String cityVillage;
    private String stateProvince;
    private String country;
    private String postalCode;
    private String latitude;
    private String longitude;
    private String countyDistrict;
}
