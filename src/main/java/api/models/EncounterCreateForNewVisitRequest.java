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
public class EncounterCreateForNewVisitRequest extends BaseModel {
    private OffsetDateTime encounterDatetime;
    private String patient;
    private String encounterType;
    private String location;
    private List<EncounterProvider> encounterProviders;
    private Visit visit;
}
