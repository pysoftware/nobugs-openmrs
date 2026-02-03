package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Search, Create, Update
public class PersonResponse extends BaseModel {
    private String uuid;
    private String display;
    private String gender;
    private Integer age;
    private String birthdate;
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
