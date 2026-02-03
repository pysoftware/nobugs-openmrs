package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonAddressCreateRequest extends BaseModel {
    private String address1;
    private String cityVillage;
    private String stateProvince;
    private int postalCode;
    private LocalDate latitude;
    private LocalDate longitude;
}
