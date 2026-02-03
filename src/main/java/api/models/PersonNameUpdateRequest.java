package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonNameUpdateRequest extends BaseModel {
    private String givenName;
    private String familyName;
    private Boolean preferred;
    private String prefix;
}
