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
public class VisitResponse extends BaseModel {
    private String uuid;
    private String display;
    private VisitPatientResponse patient;
    private VisitType visitType;
    private String indication;
    private Location location;
    private LocalDateTime startDatetime;
    private LocalDateTime stopDatetime;
    private List<Encounter> encounters;
    private List<Object> attributes;
    private Boolean voided;
    private List<Link> links;
    private String resourceVersion;
}
