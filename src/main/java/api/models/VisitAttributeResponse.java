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
public class VisitAttributeResponse extends BaseModel {
    private String display;
    private String uuid;
    private VisitAttributeType attributeType;
    private String value;
    private Boolean voided;
    private List<VisitLink> links;
    private String resourceVersion;
}
