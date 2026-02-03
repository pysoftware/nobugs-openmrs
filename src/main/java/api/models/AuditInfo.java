package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditInfo extends BaseModel {
    private Creator creator;
    private LocalDateTime dateCreated;
    private ChangedBy changedBy;
    private LocalDateTime dateChanged;
}
