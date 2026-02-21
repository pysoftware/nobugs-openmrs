package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreferredName extends BaseModel {
    private String uuid;
    private String display;
    private Boolean voided;
    private List<Link> links;
    private String givenName;       // ← добавь
    private String familyName;
    private Boolean preferred;
}
