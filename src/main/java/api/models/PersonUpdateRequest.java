package api.models;

import api.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonUpdateRequest extends BaseModel{
    private Gender gender;
    private OffsetDateTime birthdate;

}
