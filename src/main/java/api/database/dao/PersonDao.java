package api.database.dao;

import api.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDao {
    private String uuid;
    private Gender gender;
    private OffsetDateTime birthdate;
    //person_name
    private String given_name;
    private String family_name;
    //person_address
    private String address1;
    private String city_village;
    private String country;
    private String postal_code;
}
