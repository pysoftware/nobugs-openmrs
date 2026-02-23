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
public class AllergyUpdateRequest extends BaseModel {
    private AllergenRequest allergen;
    private SeverityRequest severity;
    private String comment;
    private List<AllergyReactionRequest> reactions;
}
