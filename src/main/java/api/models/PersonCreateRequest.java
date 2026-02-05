package api.models;

import api.models.enums.Gender;
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
public class PersonCreateRequest extends BaseModel {
    private List<PersonName> names;
    private Gender gender;
    private String birthdate;
    private List<PersonAddress> addresses;
}
