package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Search, Create, Update
public class AllergyResponse extends BaseModel {
    private String display;
    private String uuid;
    private AllergenResponse allergen;
    private SeverityResponse severity;
    private String comment;
    private List<AllergyReactionResponse> reactions;
    private Patient patient;
    private List<PatientLink> links;
    private String resourceVersion;
}
