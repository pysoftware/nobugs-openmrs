package api.models;

import api.generators.RegexConstants;
import api.generators.annotations.GeneratingStringRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdentifierRequest extends BaseModel {
    @GeneratingStringRule(regex = RegexConstants.PATIENT_IDENTIFIER_TYPE_FORMAT)
    private String identifier;
    @GeneratingStringRule(regex = RegexConstants.UUID_V4)
    private String identifierType;
    @GeneratingStringRule(regex = RegexConstants.UUID_V4)
    private String location;
    private Boolean preferred;
}
