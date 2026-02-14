package api.database.dao;

import api.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.OffsetDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDao {
    private String uuid;
    private Gender gender;
    private OffsetDateTime birthdate;
    private Boolean birthdateEstimated;
    private Boolean dead;
    private Boolean deathDate;
    private LocalTime birthtime;
    private Boolean deathdateEstimated;
}
