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
public class ProviderAttributeResponse extends BaseModel {
    private String display;
    private String uuid;
    private ProviderAttributeType attributeType;
    private String value;
    private Boolean voided;
    private List<ProviderLink> links;
    private String resourceVersion;
}
