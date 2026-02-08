package api.models;

import api.generators.annotations.DateFormat;
import api.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonUpdateRequest extends BaseModel{
    private Gender gender;
    @DateFormat
    private String birthdate;
}
