package api.database.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonAddressDao {
    private String uuid;
    private String address1;
    private String city_village;
    private String country;
    private String postal_code;
}
