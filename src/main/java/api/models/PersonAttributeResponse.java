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
public class PersonAttributeResponse extends BaseModel {
    private String display;
    private String uuid;
    private String value;
    private AttributeType attributeType;
    private Boolean voided;
    private List<Link> links;
    private String resourceVersion;
}
