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
public class Person extends BaseModel {
    private Gender gender;
    private Integer age;
    @GeneratingOffsetDateTimeRule(time = false)
    private OffsetDateTime birthdate;
    private Boolean birthdateEstimated;
    private Boolean dead;
    private OffsetDateTime deathDate;
    private String causeOfDeath;
    private List<PersonName> names;
    private List<PersonAddress> addresses;
}
