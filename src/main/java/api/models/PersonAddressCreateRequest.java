package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonAddressCreateRequest extends BaseModel {
    private String address1;
    private String cityVillage;
    private String stateProvince;
    private String postalCode;
    private Boolean preferred = true;
    private OffsetDateTime latitude;
    private OffsetDateTime longitude;
}
