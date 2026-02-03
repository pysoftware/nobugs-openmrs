package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person extends BaseModel {
    private String gender;
    private Integer age;
    private LocalDateTime birthdate;
    private Boolean birthdateEstimated;
    private Boolean dead;
    private Boolean deathDate;
    private String causeOfDeath;
    private List<PersonName> names;
    private List<PersonAddress> addresses;
}
