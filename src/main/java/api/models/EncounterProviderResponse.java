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
public class EncounterProviderResponse {
    private String uuid;
    private ProviderAttributeType provider;
    private EncounterRole encounterRole;
    private Boolean voided;
    private List<ProviderLink> links;
    private String resourceVersion;
}
