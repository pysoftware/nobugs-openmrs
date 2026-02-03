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
public class ProviderResponse extends BaseModel {
    private String uuid;
    private String display;
    private ProviderPerson person;
    private String identifier;
    private List<Object> attributes;
    private Boolean retired;
    private ProviderLink links;
    private String resourceVersion;
}
