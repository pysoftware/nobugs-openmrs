package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientUpdateRequest extends BaseModel {
    private String identifier;
    private String identifierType;
    private String location;
    private Boolean preferred;
}
