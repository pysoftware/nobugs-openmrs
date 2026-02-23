package api.models;

import api.generators.RegexConstants;
import api.generators.annotations.GeneratingStringRule;
import api.models.enums.LocationBehavior;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientIdentifierTypeCreateRequest extends BaseModel {
    private String name;
    private String description;
    private String format;
    @GeneratingStringRule(regex = RegexConstants.PATIENT_IDENTIFIER_TYPE_DESCRIPTION)
    private String formatDescription;
    private Boolean required;
    private String validator;
    private LocationBehavior locationBehavior;
    private String uniquenessBehavior;
}
