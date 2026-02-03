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
public class PersonCreateRequest extends BaseModel {
    private List<PersonName> names;
    private String gender;
    private LocalDateTime birthdate;
    private List<PersonAddress> addresses;
}
