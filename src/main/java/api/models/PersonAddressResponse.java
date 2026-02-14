package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Search, Create, Update
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonAddressResponse extends BaseModel {
    private String display;
    private String uuid;
    private Boolean preferred;
    private String address1;
    private String address2;
    private String cityVillage;
    private String stateProvince;
    private String country;
    private String postalCode;
    private String countyDistrict;
    private OffsetDateTime latitude;
    private OffsetDateTime longitude;
    private Boolean voided;
    private List<Link> links;
    private String resourceVersion;
}
