package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonAddress extends BaseModel {
    private String address1;
    private String cityVillage;
    private String country;
    private String postalCode;
}
