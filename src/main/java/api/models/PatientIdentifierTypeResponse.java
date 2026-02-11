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
public class PatientIdentifierTypeResponse extends BaseModel {
    private String uuid;
    private String display;
    private String name;
    private String description;
    private String format;
    private String formatDescription;
    private Boolean required;
    private String validator;
    private String locationBehavior;
    private String uniquenessBehavior;
    private Boolean retired;
    private List<Link> links;
    private String resourceVersion;
}
