package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditInfo extends BaseModel {
    private Creator creator;
    private OffsetDateTime dateCreated;
    private ChangedBy changedBy;
    private OffsetDateTime dateChanged;
}
