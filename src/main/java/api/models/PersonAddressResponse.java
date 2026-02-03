package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Search, Create, Update
public class PersonAddressResponse extends BaseModel {
    private String display;
    private String uuid;
    private Boolean preferred;
    private String address1;
    private String address2;
    private String cityVillage;
    private String stateProvince;
    private String country;
    private int postalCode;
    private String countyDistrict;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate latitude;
    private LocalDate longitude;
    private Boolean voided;
    private List<Link> links;
    private String resourceVersion;
}
