package api.models;

import api.models.enums.Gender;
import api.generators.annotations.DateFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonCreateRequest extends BaseModel {
    private List<PersonName> names;
    private Gender gender;
    @DateFormat
    private String birthdate;
    private List<PersonAddress> addresses;
}
