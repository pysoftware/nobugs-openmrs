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
public class PatientIdentifierSubResourcesResponse extends BaseModel {
    private String display;
    private String uuid;
    private String identifier;
    private IdentifierType identifierType;
    private Location location;
    private Boolean preferred;
    private Boolean voided;
    private List<Link> links;
    private String resourceVersion;
}
