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
public class PatientResponse extends BaseModel {
    private String uuid;
    private String display;
    private List<IdentifierResponse> identifiers;
    private PersonResponse person;
    private Boolean voided;
    private List<Link> links;
    private String resourceVersion;
}
