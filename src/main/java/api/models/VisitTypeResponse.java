package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Search, Create, Update
public class VisitTypeResponse extends BaseModel {
    private String uuid;
    private String display;
    private String name;
    private String description;
    private Boolean retired;
    private AuditInfo auditInfo;
    private List<Link> links;
    private String resourceVersion;
}
