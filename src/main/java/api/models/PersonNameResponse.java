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
public class PersonNameResponse extends BaseModel {
    private String display;
    private String uuid;
    private String givenName;
    private String middleName;
    private String familyName;
    private String familyName2;
    private Boolean voided;
    private List<Link> links;
    private String resourceVersion;
}
