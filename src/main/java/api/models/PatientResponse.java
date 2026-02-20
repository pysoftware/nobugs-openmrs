package api.models;

import api.models.interfaces.HasUuid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Search, Create
public class PatientResponse extends BaseModel implements HasUuid {
    private String uuid;
    private String display;
    private List<IdentifierResponse> identifiers;
    private PersonResponse person;
    private Boolean voided;
    private List<Link> links;
    private String resourceVersion;
}
