package api.models;

import api.generators.annotations.GeneratingOffsetDateTimeRule;
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
    @GeneratingOffsetDateTimeRule(time = false)
    private OffsetDateTime birthdate;
    private List<PersonAddress> addresses;
}
