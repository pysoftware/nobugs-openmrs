package api.models;

import api.models.enums.Gender;
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
public class PersonCreateRequest extends BaseModel {
    private List<PersonName> names;
    private Gender gender;
    //@DateFormat
    private OffsetDateTime birthdate;
    private List<PersonAddress> addresses;
}
