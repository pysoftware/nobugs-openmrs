package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitCreateRequest extends BaseModel {
    private String patient;
    private String visitType;
    private OffsetDateTime startDatetime;
    private String location;
    private String indication;
    private List<String> encounters;
}
