package api.models;

import lombok.*;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
// Search, Create, Update
public class PersonResponse extends BaseModel {
    private String uuid;
    private String display;
    private String gender;
    private Integer age;
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
