package api.models;

import api.generators.annotations.GeneratingOffsetDateTimeRule;
import api.models.enums.Gender;
import api.models.interfaces.HasUuid;
import lombok.*;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
// Search, Create, Update
public class PersonResponse extends BaseModel implements HasUuid {
    private String uuid;
    private String display;
    private Gender gender;
    private Integer age;
    @GeneratingOffsetDateTimeRule(time = false)
    private OffsetDateTime birthdate;
    private Boolean birthdateEstimated;
    private Boolean dead;
    private Boolean deathDate;
    private String causeOfDeath;
    private PreferredName preferredName;
    private PreferredAddress preferredAddress;
    private List<Object> attributes;
    private Boolean voided;
    private LocalTime birthtime;
    private Boolean deathdateEstimated;
    private List<Link> links;
    private String resourceVersion;
}
